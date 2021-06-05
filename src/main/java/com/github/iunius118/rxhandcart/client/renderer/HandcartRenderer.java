package com.github.iunius118.rxhandcart.client.renderer;

import com.github.iunius118.rxhandcart.client.HandcartState;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.util.Optional;

public class HandcartRenderer {
    private static final RenderType SHADOW_RENDER_TYPE = RenderType.entityShadow(new ResourceLocation("textures/misc/shadow.png"));
    private static final float SHADOW_RADIUS = 0.75F;

    // Render handcarts in third person view
    public void renderHandcartTP(PlayerEntity player, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer) {
        Optional<HandcartState> stateOptional = HandcartState.of(player, partialTick);
        if (!stateOptional.isPresent()) return;

        HandcartState state = stateOptional.get();
        World world = player.level;
        Minecraft minecraft = Minecraft.getInstance();
        EntityRendererManager entityRendererManager = minecraft.getEntityRenderDispatcher();
        EntityRenderer<? super Entity> entityRenderer = entityRendererManager.getRenderer(player);
        Vector3d renderOffset = entityRenderer.getRenderOffset(player, partialTick);
        Vector3d playerRenderPosition = player.getPosition(partialTick).add(renderOffset);

        matrixStack.pushPose();
        matrixStack.translate(-playerRenderPosition.x, -playerRenderPosition.y, -playerRenderPosition.z);

        Vector3d position = state.position;
        int light = WorldRenderer.getLightColor(world, new BlockPos(position.x, position.y, position.z));
        renderHandcart(state, matrixStack, renderBuffer, light);
        // Render handcart's shadow
        renderShadow(position, matrixStack, renderBuffer);

        matrixStack.popPose();
    }

    // Render handcart in first person view
    public void renderHandcartFP(float partialTick, MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;
        if (player == null) return;

        Optional<HandcartState> stateOptional = HandcartState.of(player, partialTick);
        if (!stateOptional.isPresent()) return;

        HandcartState state = stateOptional.get();
        World world = player.level;
        GameRenderer gameRenderer = minecraft.gameRenderer;
        ActiveRenderInfo mainCamera = gameRenderer.getMainCamera();
        Vector3d cameraPosition = mainCamera.getPosition();
        IRenderTypeBuffer renderBuffer = minecraft.renderBuffers().bufferSource();

        matrixStack.pushPose();
        matrixStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        Vector3d position = state.position;
        int light = WorldRenderer.getLightColor(world, new BlockPos(position.x, position.y, position.z));
        renderHandcart(state, matrixStack, renderBuffer, light);

        matrixStack.popPose();
    }

    private void renderHandcart(HandcartState state, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int light) {
        Vector3d position = state.position;
        float rotation = state.rotation;
        IHandcartModel model = state.model;
        ResourceLocation texture = state.texture;

        matrixStack.pushPose();

        // Render handcart model
        matrixStack.translate(position.x, position.y, position.z);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
        matrixStack.scale(1.5F, 1.5F, 1.5F);
        IVertexBuilder vertexBuilder = renderBuffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
    }

    private void renderShadow(Vector3d handcartPosition, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer){
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;

        if (player == null) return;

        // Calculate shadow strength
        GameRenderer gameRenderer = minecraft.gameRenderer;
        ActiveRenderInfo mainCamera = gameRenderer.getMainCamera();
        Vector3d cameraPosition = mainCamera.getPosition();
        double cameraDistance = cameraPosition.distanceToSqr(handcartPosition);
        float shadowStrength = (float)(1.0D - cameraDistance / 256.0D);

        if (shadowStrength <= 0) return;

        // Calculate area to render shadow
        int minX = MathHelper.floor(handcartPosition.x - (double) SHADOW_RADIUS);
        int maxX = MathHelper.floor(handcartPosition.x + (double) SHADOW_RADIUS);
        int minY = MathHelper.floor(handcartPosition.y - (double) SHADOW_RADIUS);
        int maxY = MathHelper.floor(handcartPosition.y);
        int minZ = MathHelper.floor(handcartPosition.z - (double) SHADOW_RADIUS);
        int maxZ = MathHelper.floor(handcartPosition.z + (double) SHADOW_RADIUS);

        IVertexBuilder vertexBuilder = renderBuffer.getBuffer(SHADOW_RENDER_TYPE);
        World world = player.level;

        matrixStack.pushPose();
        matrixStack.translate(handcartPosition.x, handcartPosition.y, handcartPosition.z);

        MatrixStack.Entry matrixEntry = matrixStack.last();

        for(BlockPos blockPos : BlockPos.betweenClosed(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
            EntityRendererManager.renderBlockShadow(matrixEntry, vertexBuilder, world, blockPos, handcartPosition.x, handcartPosition.y, handcartPosition.z, SHADOW_RADIUS, shadowStrength);
        }

        matrixStack.popPose();
    }
}
