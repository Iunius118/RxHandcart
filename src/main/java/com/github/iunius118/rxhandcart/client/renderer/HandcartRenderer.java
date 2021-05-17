package com.github.iunius118.rxhandcart.client.renderer;

import com.github.iunius118.rxhandcart.client.HandcartManager;
import com.github.iunius118.rxhandcart.client.HandcartState;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class HandcartRenderer {
    public void renderHandcartTP(PlayerEntity player, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer) {
        int id = player.getId();
        HandcartManager.Handcart handcart = HandcartManager.getHandcart(id);
        if (handcart == null)  return;

        World world = player.level;
        Vector3d playerPosition = player.getPosition(partialTick);

        matrixStack.pushPose();
        matrixStack.translate(-playerPosition.x, -playerPosition.y, -playerPosition.z);

        HandcartState state = handcart.getHandcartState(partialTick);
        Vector3d position = state.position;
        int light = WorldRenderer.getLightColor(world, new BlockPos(position.x, position.y, position.z));
        renderHandcart(state, partialTick, matrixStack, renderBuffer, light);

        matrixStack.popPose();
    }

    public void renderHandcartFP(float partialTick, MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;
        if (player == null) return;

        int id = player.getId();
        HandcartManager.Handcart handcart = HandcartManager.getHandcart(id);
        if (handcart == null)  return;

        World world = player.level;
        GameRenderer gameRenderer = minecraft.gameRenderer;
        ActiveRenderInfo mainCamera = gameRenderer.getMainCamera();
        Vector3d cameraPosition = mainCamera.getPosition();
        IRenderTypeBuffer renderBuffer = minecraft.renderBuffers().bufferSource();

        matrixStack.pushPose();
        matrixStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        HandcartState state = handcart.getHandcartState(partialTick);
        Vector3d position = state.position;
        int light = WorldRenderer.getLightColor(world, new BlockPos(position.x, position.y, position.z));
        renderHandcart(state, partialTick, matrixStack, renderBuffer, light);

        matrixStack.popPose();
    }

    private void renderHandcart(HandcartState state, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, int light) {
        Vector3d position = state.position;
        float rotation = state.rotation;
        IHandcartModel model = state.model;
        ResourceLocation texture = state.texture;

        matrixStack.pushPose();

        matrixStack.translate(position.x, position.y, position.z);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
        IVertexBuilder vertexBuilder = renderBuffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
    }
}
