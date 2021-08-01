package com.github.iunius118.rxhandcart.client.renderer;

import com.github.iunius118.rxhandcart.client.HandcartState;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class HandcartRenderer {
    private static final RenderType SHADOW_RENDER_TYPE = RenderType.entityShadow(new ResourceLocation("textures/misc/shadow.png"));
    private static final float SHADOW_RADIUS = 0.75F;

    // Render handcarts in third person view
    public void renderHandcartTP(Player player, float partialTick, PoseStack matrixStack, MultiBufferSource renderBuffer) {
        Optional<HandcartState> stateOptional = HandcartState.of(player, partialTick);
        if (!stateOptional.isPresent()) return;

        HandcartState state = stateOptional.get();
        Level world = player.level;
        Minecraft minecraft = Minecraft.getInstance();
        EntityRenderDispatcher entityRendererManager = minecraft.getEntityRenderDispatcher();
        EntityRenderer<? super Entity> entityRenderer = entityRendererManager.getRenderer(player);
        Vec3 renderOffset = entityRenderer.getRenderOffset(player, partialTick);
        Vec3 playerRenderPosition = player.getPosition(partialTick).add(renderOffset);

        matrixStack.pushPose();
        matrixStack.translate(-playerRenderPosition.x, -playerRenderPosition.y, -playerRenderPosition.z);

        Vec3 position = state.position;
        int light = LevelRenderer.getLightColor(world, new BlockPos(position.x, position.y, position.z));
        renderHandcart(state, matrixStack, renderBuffer, light);
        // Render handcart's shadow
        renderShadow(position, matrixStack, renderBuffer);

        matrixStack.popPose();
    }

    // Render handcart in first person view
    public void renderHandcartFP(float partialTick, PoseStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) return;

        Optional<HandcartState> stateOptional = HandcartState.of(player, partialTick);
        if (!stateOptional.isPresent()) return;

        HandcartState state = stateOptional.get();
        Level world = player.level;
        GameRenderer gameRenderer = minecraft.gameRenderer;
        Camera mainCamera = gameRenderer.getMainCamera();
        Vec3 cameraPosition = mainCamera.getPosition();
        MultiBufferSource renderBuffer = minecraft.renderBuffers().bufferSource();

        matrixStack.pushPose();
        matrixStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        Vec3 position = state.position;
        int light = LevelRenderer.getLightColor(world, new BlockPos(position.x, position.y, position.z));
        renderHandcart(state, matrixStack, renderBuffer, light);

        matrixStack.popPose();
    }

    private void renderHandcart(HandcartState state, PoseStack matrixStack, MultiBufferSource renderBuffer, int light) {
        Vec3 position = state.position;
        float rotation = state.rotation;
        IHandcartModel model = state.model;
        ResourceLocation texture = state.texture;

        matrixStack.pushPose();

        // Render handcart model
        matrixStack.translate(position.x, position.y, position.z);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
        matrixStack.scale(1.5F, 1.5F, 1.5F);
        VertexConsumer vertexBuilder = renderBuffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
    }

    private void renderShadow(Vec3 handcartPosition, PoseStack matrixStack, MultiBufferSource renderBuffer){
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player == null) return;

        // Calculate shadow strength
        GameRenderer gameRenderer = minecraft.gameRenderer;
        Camera mainCamera = gameRenderer.getMainCamera();
        Vec3 cameraPosition = mainCamera.getPosition();
        double cameraDistance = cameraPosition.distanceToSqr(handcartPosition);
        float shadowStrength = (float)(1.0D - cameraDistance / 256.0D);

        if (shadowStrength <= 0) return;

        // Calculate area to render shadow
        int minX = Mth.floor(handcartPosition.x - (double) SHADOW_RADIUS);
        int maxX = Mth.floor(handcartPosition.x + (double) SHADOW_RADIUS);
        int minY = Mth.floor(handcartPosition.y - (double) SHADOW_RADIUS);
        int maxY = Mth.floor(handcartPosition.y);
        int minZ = Mth.floor(handcartPosition.z - (double) SHADOW_RADIUS);
        int maxZ = Mth.floor(handcartPosition.z + (double) SHADOW_RADIUS);

        VertexConsumer vertexBuilder = renderBuffer.getBuffer(SHADOW_RENDER_TYPE);
        Level world = player.level;

        matrixStack.pushPose();
        matrixStack.translate(handcartPosition.x, handcartPosition.y, handcartPosition.z);

        PoseStack.Pose matrixEntry = matrixStack.last();

        for(BlockPos blockPos : BlockPos.betweenClosed(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
            EntityRenderDispatcher.renderBlockShadow(matrixEntry, vertexBuilder, world, blockPos, handcartPosition.x, handcartPosition.y, handcartPosition.z, SHADOW_RADIUS, shadowStrength);
        }

        matrixStack.popPose();
    }
}
