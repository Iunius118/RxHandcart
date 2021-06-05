package com.github.iunius118.rxhandcart.network;

import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class ChangeCartMessage {
    private final int entityId;
    private final int handcartType;

    public ChangeCartMessage(Entity owner, int type) {
        entityId = owner.getId();
        handcartType = type;
    }

    private ChangeCartMessage(int ownerEntityId, int type) {
        entityId = ownerEntityId;
        handcartType = type;
    }

    public static void encode(ChangeCartMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.handcartType);
    }

    public static ChangeCartMessage decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        int handcartType = buf.readInt();
        return new ChangeCartMessage(entityId, handcartType);
    }

    public static void handle(ChangeCartMessage msg, Supplier<NetworkEvent.Context> ctx) {
        changeCart(msg);
        ctx.get().setPacketHandled(true);
    }

    private static void changeCart(ChangeCartMessage msg) {
        if (FMLLoader.getDist().isDedicatedServer()) return;
        // Only in client

        ClientWorld level = Minecraft.getInstance().level;
        if (level == null) return;

        Entity entity = level.getEntity(msg.entityId);
        if (entity == null) return;

        Optional<IHandcartHandler> capability = entity.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        if (!capability.isPresent()) return;

        IHandcartHandler handcartHandler = capability.get();
        handcartHandler.setType(msg.handcartType);
    }
}
