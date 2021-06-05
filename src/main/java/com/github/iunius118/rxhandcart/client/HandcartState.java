package com.github.iunius118.rxhandcart.client;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.capability.HandcartHandler;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import java.util.Optional;
import java.util.OptionalInt;

public class HandcartState {
    public final Vector3d position;
    public final float rotation;
    public final IHandcartModel model;
    public final ResourceLocation texture;

    private HandcartState(Vector3d pos, float rot, IHandcartModel handcartModel, ResourceLocation textureLocation) {
        position = pos;
        rotation = rot;
        model = handcartModel;
        texture = textureLocation;
    }

    public static Optional<HandcartState> of(PlayerEntity owner, float partialTick) {
        OptionalInt handcartType = RxHandcart.getHandcartType(owner);
        if (!handcartType.isPresent()) return Optional.empty();

        int type = handcartType.getAsInt();
        if (type == HandcartHandler.INVISIBLE_TYPE) return Optional.empty();

        IHandcartModel handcartModel = HandcartManager.getHandcartModel(type);
        ResourceLocation textureLocation = HandcartManager.getHandcartTexture(type);
        if (handcartModel == null || textureLocation == null) return Optional.empty();

        Vector3d ownerMotion = owner.getDeltaMovement();
        Vector3d ownerHorizontalMotion = new Vector3d(ownerMotion.x, 0, ownerMotion.z);
        Vector3d ownerPos = owner.getPosition(partialTick);
        double posX;
        double posZ;
        float rot;
        float distance = 1.2F;

        if (ownerHorizontalMotion.lengthSqr() < 0.0001) {
            // Owner is almost stopped
            rot = -getBodyRotation(owner, partialTick);
            float dx = MathHelper.sin(rot * ((float) Math.PI / 180F) - (float) Math.PI);
            float dz = MathHelper.cos(rot * ((float) Math.PI / 180F) - (float) Math.PI);
            posX = ownerPos.x + dx * distance;
            posZ = ownerPos.z + dz * distance;

        } else {
            // Owner is moving
            Vector3d d = ownerHorizontalMotion.normalize();
            posX = ownerPos.x - d.x * distance;
            posZ = ownerPos.z - d.z * distance;
            rot = (float) (Math.atan2(d.x, d.z) * (double) (180F / (float) Math.PI));
        }

        double posY = getPositionY(owner.level, new BlockPos(posX, ownerPos.y, posZ));
        return Optional.of(new HandcartState(new Vector3d(posX, posY, posZ), rot, handcartModel, textureLocation));
    }

    private static float getBodyRotation(Entity entity, float partialTick) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            return partialTick == 1.0F ? livingEntity.yBodyRot : MathHelper.lerp(partialTick, livingEntity.yBodyRotO, livingEntity.yBodyRot);
        } else {
            return entity.getViewYRot(partialTick);
        }
    }

    private static double getPositionY(World world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int worldHeight = world.getHeight(Heightmap.Type.MOTION_BLOCKING, x, z);
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

    private static double getBlockHeight(World world, BlockPos pos) {
        BlockState block = world.getBlockState(pos);
        VoxelShape shape = block.getCollisionShape(world, pos);

        if (shape == VoxelShapes.empty()) {
            return 0;
        }else if (shape == VoxelShapes.block()) {
            return 1;
        } else {
            return shape.max(Direction.Axis.Y);
        }
    }
}
