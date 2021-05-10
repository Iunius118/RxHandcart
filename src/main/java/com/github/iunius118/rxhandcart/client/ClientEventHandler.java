package com.github.iunius118.rxhandcart.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            // Update handcart list
            HandcartManager.tick();
        }
    }

    @SubscribeEvent
    public void onPostRenderPlayerEvent(RenderPlayerEvent.Post event) {
        PlayerEntity player = event.getPlayer();
        float partialTick = event.getPartialRenderTick();
        IRenderTypeBuffer renderBuffer = event.getBuffers();
        MatrixStack matrixStack = event.getMatrixStack();
        // Render handcarts in third person view
        renderHandcartTP(player, partialTick, matrixStack, renderBuffer);
    }

    private void renderHandcartTP(PlayerEntity player, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderBuffer) {
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

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        GameSettings options = Minecraft.getInstance().options;
        if (options.getCameraType() == PointOfView.FIRST_PERSON) {
            // Render handcart in first person view
            renderHandcartFP(event.getPartialTicks(), event.getMatrixStack());
        }
    }

    private void renderHandcartFP(float partialTick, MatrixStack matrixStack) {
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
        EntityModel<Entity> model = state.model;
        ResourceLocation texture = state.texture;

        matrixStack.pushPose();

        matrixStack.translate(position.x, position.y + 0.4, position.z);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation + 90));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
        IVertexBuilder vertexBuilder = renderBuffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.setupAnim(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
        model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
    }
}
