package com.github.iunius118.rxhandcart.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

public interface IHandcartModel {
    void render(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int lightmapCoord, int overlayColor, float red, float green, float blue, float alpha);
}
