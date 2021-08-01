package com.github.iunius118.rxhandcart.client;

import com.github.iunius118.rxhandcart.client.model.HandcartModel;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class HandcartManager {
    private static final HandcartModel HANDCART_MODEL_1 = new HandcartModel();
    private static final ResourceLocation HANDCART_LOCATION_1 = new ResourceLocation("textures/block/oak_planks.png");

    @Nullable
    public static IHandcartModel getHandcartModel(int type) {
        if (type == 1) {
            return HANDCART_MODEL_1;
        }

        return null;
    }

    @Nullable
    public static ResourceLocation getHandcartTexture(int type) {
        if (type == 1) {
            return HANDCART_LOCATION_1;
        }

        return null;
    }
}
