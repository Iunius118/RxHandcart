package com.github.iunius118.rxhandcart.client.renderer;

import com.github.iunius118.rxhandcart.client.HandcartState;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.Optional;

public class HandcartRenderer {
    private static final RenderType SHADOW_RENDER_TYPE = RenderType.entityShadow(new ResourceLocation("textures/misc/shadow.png"));
    private static final float SHADOW_RADIUS = 0.75F;

    // Render handcarts in third person view
    public void renderHandcartTP(Player player, float partialTick, PoseStack matrixStack, MultiBufferSource renderBuffer) {
        Optional<HandcartState> stateOptional = HandcartState.of(player, partialTick);
        if (stateOptional.isEmpty())
            return;

        HandcartState state = stateOptional.get();
        Level world = player.level();
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
        if (player == null)
            return;

        Optional<HandcartState> stateOptional = HandcartState.of(player, partialTick);
        if (stateOptional.isEmpty())
            return;

        HandcartState state = stateOptional.get();
        Level world = player.level();
        GameRenderer gameRenderer = minecraft.gameRenderer;
        Camera mainCamera = gameRenderer.getMainCamera();
        Vec3 cameraPosition = mainCamera.getPosition();
        MultiBufferSource renderBuffer = minecraft.renderBuffers().bufferSource();

        matrixStack.pushPose();
        matrixStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        Vec3 position = state.position;
        int light = LevelRenderer.getLightColor(world, new BlockPos.MutableBlockPos(position.x, position.y, position.z));
        renderHandcart(state, matrixStack, renderBuffer, light);
        // Render handcart's shadow
        renderShadow(position, matrixStack, renderBuffer);

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
        matrixStack.mulPoseMatrix(new Matrix4f().rotate((float) Math.toRadians(rotation), 0.0f, 1.0f, 0.0f));
        matrixStack.scale(1.5F, 1.5F, 1.5F);
        VertexConsumer vertexBuilder = renderBuffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
    }

    private void renderShadow(Vec3 handcartPosition, PoseStack matrixStack, MultiBufferSource renderBuffer){
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;

        if (player == null)
            return;

        float shadowStrength = getShadowStrength(handcartPosition);

        if (shadowStrength <= 0)
            return;

        HandcartShadowRenderer blockShadowRenderer = (HandcartShadowRenderer) minecraft.getEntityRenderDispatcher();
        VertexConsumer vertexBuilder = renderBuffer.getBuffer(SHADOW_RENDER_TYPE);
        var context = new RenderHandcartShadowContext(matrixStack, vertexBuilder, player.level(), handcartPosition, SHADOW_RADIUS, shadowStrength);
        blockShadowRenderer.rxHandcart$renderHandcartShadow(context);
    }

    private float getShadowStrength(Vec3 handcartPosition) {
        // Calculate shadow strength
        var minecraft = Minecraft.getInstance();
        var gameRenderer = minecraft.gameRenderer;
        var mainCamera = gameRenderer.getMainCamera();
        Vec3 cameraPosition = mainCamera.getPosition();
        double cameraDistance = cameraPosition.distanceToSqr(handcartPosition);
        return  (float)(1.0D - cameraDistance / 256.0D);
    }

    public record RenderHandcartShadowContext(PoseStack matrixStack, VertexConsumer vertexBuilder, Level level, Vec3 handcartPosition, float shadowRadius, float shadowStrength) {}
}
