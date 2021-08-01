package com.github.iunius118.rxhandcart.client;

import com.github.iunius118.rxhandcart.client.renderer.HandcartRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {
    private final HandcartRenderer handcartRenderer = new HandcartRenderer();

    @SubscribeEvent
    public void onPostRenderPlayerEvent(RenderPlayerEvent.Post event) {
        Player player = event.getPlayer();
        float partialTick = event.getPartialRenderTick();
        MultiBufferSource renderBuffer = event.getBuffers();
        PoseStack matrixStack = event.getMatrixStack();

        // Render handcarts in third person view
        handcartRenderer.renderHandcartTP(player, partialTick, matrixStack, renderBuffer);
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        Options options = Minecraft.getInstance().options;
        if (options.getCameraType() != CameraType.FIRST_PERSON) return;

        // Render handcart in first person view
        handcartRenderer.renderHandcartFP(event.getPartialTicks(), event.getMatrixStack());
    }
}
