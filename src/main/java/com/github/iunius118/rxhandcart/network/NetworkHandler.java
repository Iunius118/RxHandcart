package com.github.iunius118.rxhandcart.network;

import com.github.iunius118.rxhandcart.RxHandcart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String NETWORK_VERSION = "RHCv1";

    private static final ResourceLocation CHANGE_CART_CHANNEL_RESOURCE = new ResourceLocation(RxHandcart.MOD_ID, "change_cart");
    private static final SimpleChannel CHANGE_CART_CHANNEL;

    static {
        CHANGE_CART_CHANNEL = NetworkRegistry.ChannelBuilder
                .named(CHANGE_CART_CHANNEL_RESOURCE)
                .clientAcceptedVersions(v -> true)
                .serverAcceptedVersions(v -> true)
                .networkProtocolVersion(() -> NETWORK_VERSION)
                .simpleChannel();

        CHANGE_CART_CHANNEL.messageBuilder(ChangeCartMessage.class, 0)
                .encoder(ChangeCartMessage::encode)
                .decoder(ChangeCartMessage::decode)
                .consumerMainThread(ChangeCartMessage::handle)
                .add();
    }

    public SimpleChannel getChangeCartChannel() {
        return CHANGE_CART_CHANNEL;
    }

    public void sendChangeCartPacket(Entity owner, int type, ServerPlayer receiver) {
        PacketDistributor.PacketTarget target = PacketDistributor.PLAYER.with(() -> receiver);
        ChangeCartMessage message = new ChangeCartMessage(owner, type);
        CHANGE_CART_CHANNEL.send(target, message);
    }

    public void broadcastChangeCartPacket(Entity owner, int type) {
        PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> owner);
        ChangeCartMessage message = new ChangeCartMessage(owner, type);
        CHANGE_CART_CHANNEL.send(target, message);
    }
}
