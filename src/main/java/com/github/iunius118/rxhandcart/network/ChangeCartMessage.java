package com.github.iunius118.rxhandcart.network;

import com.github.iunius118.rxhandcart.client.ClientMessageHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

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

    public static void encode(ChangeCartMessage msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.handcartType);
    }

    public static ChangeCartMessage decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int handcartType = buf.readInt();
        return new ChangeCartMessage(entityId, handcartType);
    }

    public static void handle(ChangeCartMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientMessageHandler.changeCart(msg))
        );
        ctx.get().setPacketHandled(true);
    }

    public int getEntityId() {
        return entityId;
    }

    public int getHandcartType() {
        return handcartType;
    }
}
