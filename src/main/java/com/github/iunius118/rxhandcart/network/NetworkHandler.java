package com.github.iunius118.rxhandcart.network;

import com.github.iunius118.rxhandcart.RxHandcart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
                .consumer(ChangeCartMessage::handle)
                .add();
    }

    public SimpleChannel getChangeCartChannel() {
        return CHANGE_CART_CHANNEL;
    }
}
