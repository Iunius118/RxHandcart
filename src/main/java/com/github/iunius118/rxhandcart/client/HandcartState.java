package com.github.iunius118.rxhandcart.client;

import com.github.iunius118.rxhandcart.client.model.HandcartModel;
import com.github.iunius118.rxhandcart.client.model.IHandcartModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class HandcartState {
    public final Vector3d position;
    public final float rotation;
    public final IHandcartModel model;
    public final ResourceLocation texture;
    private static final HandcartModel HANDCART_MODEL = new HandcartModel();
    private static final ResourceLocation HANDCART_LOCATION = new ResourceLocation("textures/block/oak_planks.png");

    public HandcartState(Vector3d pos, float rot) {
        position = pos;
        rotation = rot;
        model = HANDCART_MODEL;
        texture = HANDCART_LOCATION;
    }
}
