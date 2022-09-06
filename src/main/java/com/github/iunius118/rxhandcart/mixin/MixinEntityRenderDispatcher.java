package com.github.iunius118.rxhandcart.mixin;

import com.github.iunius118.rxhandcart.client.renderer.HandcartRenderer;
import com.github.iunius118.rxhandcart.client.renderer.HandcartShadowRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher implements HandcartShadowRenderer {
    @Shadow
    private static void renderBlockShadow(PoseStack.Pose pose, VertexConsumer vertexConsumer, LevelReader levelReader, BlockPos blockPos, double x, double y, double z, float radius, float strength) {}

    @Override
    public void renderHandcartShadow(HandcartRenderer.RenderHandcartShadowContext context) {
        double x = context.handcartPosition().x;
        double y = context.handcartPosition().y;
        double z = context.handcartPosition().z;
        float shadowRadius = context.shadowRadius();
        PoseStack matrixStack = context.matrixStack();

        int minX = Mth.floor(x - (double) shadowRadius);
        int maxX = Mth.floor(x + (double) shadowRadius);
        int minY = Mth.floor(y - (double) shadowRadius);
        int maxY = Mth.floor(y);
        int minZ = Mth.floor(z - (double) shadowRadius);
        int maxZ = Mth.floor(z + (double) shadowRadius);

        matrixStack.pushPose();
        matrixStack.translate(x, y, z);

        PoseStack.Pose matrixEntry = matrixStack.last();

        for(BlockPos blockPos : BlockPos.betweenClosed(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
            renderBlockShadow(matrixEntry, context.vertexBuilder(), context.level(), blockPos, x, y, z, shadowRadius, context.shadowStrength());
        }

        matrixStack.popPose();
    }
}
