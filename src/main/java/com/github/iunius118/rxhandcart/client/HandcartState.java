package com.github.iunius118.rxhandcart.client;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class HandcartState {
    public final Vector3d position;
    public final float rotation;
    public final EntityModel<Entity> model;
    public final ResourceLocation texture;
    private static final ResourceLocation HANDCART_LOCATION = new ResourceLocation("textures/entity/minecart.png");

    public HandcartState(Vector3d pos, float rot) {
        position = pos;
        rotation = rot;
        model = new MinecartModel<>();
        texture = HANDCART_LOCATION;
    }
}
