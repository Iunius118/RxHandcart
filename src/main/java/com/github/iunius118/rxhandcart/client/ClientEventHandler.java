package com.github.iunius118.rxhandcart.client;

import com.github.iunius118.rxhandcart.client.renderer.HandcartRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {
    private final HandcartRenderer handcartRenderer = new HandcartRenderer();

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
        handcartRenderer.renderHandcartTP(player, partialTick, matrixStack, renderBuffer);
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        GameSettings options = Minecraft.getInstance().options;
        if (options.getCameraType() == PointOfView.FIRST_PERSON) {
            // Render handcart in first person view
            handcartRenderer.renderHandcartFP(event.getPartialTicks(), event.getMatrixStack());
        }
    }
}
