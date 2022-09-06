package com.github.iunius118.rxhandcart.client;

import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import com.github.iunius118.rxhandcart.network.ChangeCartMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

public class ClientMessageHandler {
    public static void changeCart(ChangeCartMessage msg) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null)
            return;

        Entity entity = level.getEntity(msg.getEntityId());
        if (entity == null)
            return;

        Optional<IHandcartHandler> capability = entity.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        if (capability.isEmpty())
            return;

        IHandcartHandler handcartHandler = capability.get();
        handcartHandler.setType(msg.getHandcartType());
    }
}
