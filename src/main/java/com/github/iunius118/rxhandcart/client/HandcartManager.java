package com.github.iunius118.rxhandcart.client;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandcartManager {
    private static final List<Map<Integer, Handcart>> handcartMaps = new ArrayList<>();
    private static int index = 0;
    static {
        handcartMaps.add(new HashMap<>());
        handcartMaps.add(new HashMap<>());
    }

    public static void addHandcart(Entity entity) {
        // Add handcart to back side map
        Map<Integer, Handcart> handcarts = handcartMaps.get(1 - index);
        handcarts.put(entity.getId(), new Handcart(entity));
    }

    @Nullable
    public static Handcart getHandcart(int entityId) {
        // Get handcart from front side map
        Map<Integer, Handcart> handcarts = handcartMaps.get(index);
        return handcarts.get(entityId);
    }

    public static void tick() {
        // Reverse index
        index = 1 - index;

        Map<Integer, Handcart> prevHandcarts = handcartMaps.get(1 - index);
        // Clear back side map
        prevHandcarts.clear();
    }

    public static class Handcart {
        private final Entity owner;

        public Handcart(Entity entity) {
            owner = entity;
        }

        public boolean isValid() {
            return owner.isAlive();
        }

        public HandcartState getHandcartState(float partialTick) {
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
            return new HandcartState(new Vector3d(posX, posY, posZ), rot);
        }

        private float getBodyRotation(Entity entity, float partialTick) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                return partialTick == 1.0F ? livingEntity.yBodyRot : MathHelper.lerp(partialTick, livingEntity.yBodyRotO, livingEntity.yBodyRot);
            } else {
                return entity.getViewYRot(partialTick);
            }
        }

        private double getPositionY(World world, BlockPos pos) {
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

        private double getBlockHeight(World world, BlockPos pos) {
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

        @Override
        public boolean equals(Object obj) {
            return owner.equals(obj);
        }
    }

}
