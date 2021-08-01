package com.github.iunius118.rxhandcart.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public interface IHandcartModel {
    default void render(PoseStack matrixStack, VertexConsumer vertexBuilder, int lightmapCoord, int overlayColor, float red, float green, float blue, float alpha){}
}
