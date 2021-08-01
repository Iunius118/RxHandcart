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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.OptionalInt;

public class HandcartState {
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
        if (!handcartType.isPresent()) return Optional.empty();

        int type = handcartType.getAsInt();
        if (type == HandcartHandler.INVISIBLE_TYPE) return Optional.empty();

        IHandcartModel handcartModel = HandcartManager.getHandcartModel(type);
        ResourceLocation textureLocation = HandcartManager.getHandcartTexture(type);
        if (handcartModel == null || textureLocation == null) return Optional.empty();

        Vec3 ownerMotion = owner.getDeltaMovement();
        Vec3 ownerHorizontalMotion = new Vec3(ownerMotion.x, 0, ownerMotion.z);
        Vec3 ownerPos = owner.getPosition(partialTick);
        double posX;
        double posZ;
        float rot;
        float distance = 1.2F;

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

        double posY = getPositionY(owner.level, new BlockPos(posX, ownerPos.y, posZ));
        return Optional.of(new HandcartState(new Vec3(posX, posY, posZ), rot, handcartModel, textureLocation));
    }

    private static float getBodyRotation(Entity entity, float partialTick) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            return partialTick == 1.0F ? livingEntity.yBodyRot : Mth.lerp(partialTick, livingEntity.yBodyRotO, livingEntity.yBodyRot);
        } else {
            return entity.getViewYRot(partialTick);
        }
    }

    private static double getPositionY(Level world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int worldHeight = world.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
        BlockPos highestBlockPos = new BlockPos(x, worldHeight, z);

        if (worldHeight <= y) {
            return getBlockHeight(world, highestBlockPos) + (double) worldHeight;
        }

        // Handcart's pos is higher than owner's one
        for (int h = y; h < worldHeight; h++) {
            double blockHeight = getBlockHeight(world, new BlockPos(x, h, z));

            if (blockHeight < 1.0D) {
                return h + blockHeight;
            }
        }

        return getBlockHeight(world, highestBlockPos) + (double) worldHeight;
    }

    private static double getBlockHeight(Level world, BlockPos pos) {
        BlockState block = world.getBlockState(pos);
        VoxelShape shape = block.getCollisionShape(world, pos);

        if (shape == Shapes.empty()) {
            return 0;
        }else if (shape == Shapes.block()) {
            return 1;
        } else {
            return shape.max(Direction.Axis.Y);
        }
    }
}
