package com.github.iunius118.rxhandcart.client;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.capability.HandcartHandler;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.OptionalInt;

public class HandcartState {
    public static final float DEFAULT_DISTANCE = 1.2F;
    public final Vec3 position;
    public final float rotation;
    public final IHandcartModel model;
    public final ResourceLocation texture;

    private HandcartState(Vec3 pos, float rot, IHandcartModel handcartModel, ResourceLocation textureLocation) {
        position = pos;
        rotation = rot;
        model = handcartModel;
        texture = textureLocation;
    }

    public static Optional<HandcartState> of(Player owner, float partialTick) {
        OptionalInt handcartType = RxHandcart.getHandcartType(owner);
        if (handcartType.isEmpty())
            return Optional.empty();

        int type = handcartType.getAsInt();
        if (type == HandcartHandler.INVISIBLE_TYPE)
            return Optional.empty();

        IHandcartModel handcartModel = HandcartManager.getHandcartModel(type);
        ResourceLocation textureLocation = HandcartManager.getHandcartTexture(type);
        if (handcartModel == null || textureLocation == null)
            return Optional.empty();

        Vec3 ownerMotion = owner.getDeltaMovement();
        Vec3 ownerHorizontalMotion = new Vec3(ownerMotion.x, 0, ownerMotion.z);
        Vec3 ownerPos = owner.getPosition(partialTick);
        double posX;
        double posZ;
        float rot;
        float distance = DEFAULT_DISTANCE;

        if (ownerHorizontalMotion.lengthSqr() < 0.0001) {
            // Owner is almost stopped
            rot = -getBodyRotation(owner, partialTick);
            float dx = Mth.sin(rot * ((float) Math.PI / 180F) - (float) Math.PI);
            float dz = Mth.cos(rot * ((float) Math.PI / 180F) - (float) Math.PI);
            posX = ownerPos.x + dx * distance;
            posZ = ownerPos.z + dz * distance;

        } else {
            // Owner is moving
            Vec3 d = ownerHorizontalMotion.normalize();
            posX = ownerPos.x - d.x * distance;
            posZ = ownerPos.z - d.z * distance;
            rot = (float) (Math.atan2(d.x, d.z) * (double) (180F / (float) Math.PI));
        }

        double posY = getPositionY(owner.level(), new BlockPos.MutableBlockPos(posX, ownerPos.y, posZ), ownerPos.y);
        return Optional.of(new HandcartState(new Vec3(posX, posY, posZ), rot, handcartModel, textureLocation));
    }

    private static float getBodyRotation(Entity entity, float partialTick) {
        if (entity instanceof LivingEntity livingEntity) {
            return partialTick == 1.0F ? livingEntity.yBodyRot : Mth.lerp(partialTick, livingEntity.yBodyRotO, livingEntity.yBodyRot);
        } else {
            return entity.getViewYRot(partialTick);
        }
    }

    private static double getPositionY(Level level, final BlockPos pos, final double ownerY) {
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        final int worldHeight = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
        final int minBuildHeight = level.getMinBuildHeight();

        if (y < minBuildHeight) {
            // The owner is in the void
            return ownerY;
        } else if (worldHeight <= minBuildHeight) {
            // The handcart is above the void
            return minBuildHeight;
        } else if (worldHeight - 1 <= y) {
            // The handcart is under the sky
            return getBlockHeight(level, new BlockPos(x, worldHeight - 1, z)) + worldHeight - 1;
        }

        // Search the level of the handcart from the level of the OWNER
        double blockHeight = getBlockHeight(level, pos);
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(x, y, z);

        if (blockHeight <= -1.0D) {
            // The handcart is in fluid
            return ownerY;
        } else if (blockHeight <= 0.0D) {
            // The handcart is under a roof
            for (int h = y - 1; h >= minBuildHeight; h--) {
                // Search downward
                blockHeight = getBlockHeight(level, blockPos.setY(h));

                if (blockHeight > 0.0D) {
                    return h + blockHeight;
                }
            }

            return ownerY;
        } else if(blockHeight < 1.0D) {
            return y + blockHeight;
        } else {
            // The handcart is higher than the owner
            for (int h = y + 1; h < worldHeight; h++) {
                // Search upward
                blockHeight = getBlockHeight(level, blockPos.setY(h));

                if (blockHeight <= -1.0D) {
                    // The handcart is in fluid
                    return h;
                } if (blockHeight < 1.0D) {
                    return h + blockHeight;
                }
            }

            return worldHeight;
        }
    }

    private static double getBlockHeight(Level level, BlockPos pos) {
        BlockState block = level.getBlockState(pos);
        VoxelShape shape = block.getVisualShape(level, pos, CollisionContext.empty());

        if (shape == Shapes.empty()) {
            FluidState fluidState = block.getFluidState();

            if (fluidState.isEmpty()) {
                // Shapeless like air
                return 0.0D;
            }

            // Fluid
            float h = fluidState.getHeight(level, pos);

            if (h >= 1.0D) {
                // The handcart is in fluid
                return -1.0D;
            } else {
                // The handcart floats on fluid
                return fluidState.getOwnHeight();
            }
        }else if (shape == Shapes.block()) {
            return 1.0D;
        } else {
            return shape.max(Direction.Axis.Y);
        }
    }
}
