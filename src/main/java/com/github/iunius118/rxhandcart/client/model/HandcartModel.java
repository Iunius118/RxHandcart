package com.github.iunius118.rxhandcart.client.model;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

public class HandcartModel extends SimpleModel implements IHandcartModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(RxHandcart.MOD_ID, "textures/entity/handcart/handcart_1.png");
    public static final List<SimpleQuad> HANDCART_QUADS;

    @Override
    public void render(PoseStack matrixStack, VertexConsumer vertexBuilder, int lightmapCoord, int overlayColor, float red, float green, float blue, float alpha) {
        renderQuads(matrixStack, vertexBuilder, HANDCART_QUADS, lightmapCoord, overlayColor, red, green, blue, alpha);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector4f c0 = new Vector4f(1F, 1F, 1F, 1F); // Cart_down color
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 1F); // Cart_front color
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 1F); // Cart_back color
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 1F); // Cart_left color
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 1F); // Cart_right color
        Vector4f c5 = new Vector4f(1F, 1F, 1F, 1F); // Wheel_left color
        Vector4f c6 = new Vector4f(1F, 1F, 1F, 1F); // Wheel_right color
        Vector4f c7 = new Vector4f(1F, 1F, 1F, 1F); // Handle_bar color
        Vector4f c8 = new Vector4f(1F, 1F, 1F, 1F); // Handle_left color
        Vector4f c9 = new Vector4f(1F, 1F, 1F, 1F); // Handle_right color

        Vector3f v47 = new Vector3f(0.250000F, 0.437500F, 0.437500F);
        Vector3f v55 = new Vector3f(0.250000F, 0.437500F, 0.250000F);
        Vector3f v43 = new Vector3f(-0.375000F, 0.000000F, 0.000000F);
        Vector3f v32 = new Vector3f(0.312500F, 0.312500F, -0.312500F);
        Vector3f v9 = new Vector3f(0.250000F, 0.500000F, 0.187500F);
        Vector3f v44 = new Vector3f(0.250000F, 0.500000F, 0.500000F);
        Vector3f v198 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v194 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v3 = new Vector3f(0.250000F, 0.125000F, -0.437500F);
        Vector3f v15 = new Vector3f(0.250000F, 0.500000F, -0.500000F);
        Vector3f v39 = new Vector3f(-0.312500F, 0.000000F, -0.312500F);
        Vector3f v54 = new Vector3f(0.312500F, 0.437500F, 0.250000F);
        Vector3f v22 = new Vector3f(0.312500F, 0.125000F, 0.250000F);
        Vector3f v14 = new Vector3f(0.250000F, 0.500000F, -0.437500F);
        Vector3f v195 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v35 = new Vector3f(0.312500F, 0.000000F, 0.000000F);
        Vector3f v42 = new Vector3f(-0.375000F, 0.000000F, -0.312500F);
        Vector3f v197 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v8 = new Vector3f(0.250000F, 0.500000F, 0.250000F);
        Vector3f v59 = new Vector3f(-0.312500F, 0.437500F, 0.500000F);
        Vector3f v196 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v41 = new Vector3f(-0.375000F, 0.312500F, 0.000000F);
        Vector3f v27 = new Vector3f(-0.312500F, 0.125000F, 0.250000F);
        Vector3f v40 = new Vector3f(-0.375000F, 0.312500F, -0.312500F);
        Vector3f v36 = new Vector3f(-0.312500F, 0.312500F, 0.000000F);
        Vector3f v0 = new Vector3f(0.250000F, 0.187500F, 0.187500F);
        Vector3f v52 = new Vector3f(0.312500F, 0.500000F, 0.500000F);
        Vector3f v31 = new Vector3f(0.375000F, 0.000000F, -0.312500F);
        Vector3f v48 = new Vector3f(-0.250000F, 0.500000F, 0.437500F);
        Vector3f v199 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v53 = new Vector3f(0.312500F, 0.437500F, 0.500000F);
        Vector3f v23 = new Vector3f(0.312500F, 0.125000F, -0.500000F);
        Vector3f v56 = new Vector3f(-0.250000F, 0.437500F, 0.250000F);
        Vector3f v37 = new Vector3f(-0.312500F, 0.312500F, -0.312500F);
        Vector3f v17 = new Vector3f(-0.250000F, 0.500000F, -0.500000F);
        Vector3f v24 = new Vector3f(-0.312500F, 0.500000F, -0.500000F);
        Vector3f v21 = new Vector3f(0.312500F, 0.500000F, -0.500000F);
        Vector3f v7 = new Vector3f(-0.250000F, 0.125000F, 0.187500F);
        Vector3f v29 = new Vector3f(0.375000F, 0.312500F, -0.312500F);
        Vector3f v28 = new Vector3f(0.375000F, 0.312500F, 0.000000F);
        Vector3f v19 = new Vector3f(-0.250000F, 0.125000F, -0.500000F);
        Vector3f v45 = new Vector3f(0.250000F, 0.500000F, 0.437500F);
        Vector3f v1 = new Vector3f(0.250000F, 0.187500F, -0.437500F);
        Vector3f v33 = new Vector3f(0.312500F, 0.312500F, 0.000000F);
        Vector3f v2 = new Vector3f(0.250000F, 0.125000F, 0.187500F);
        Vector3f v12 = new Vector3f(-0.250000F, 0.500000F, 0.250000F);
        Vector3f v46 = new Vector3f(0.250000F, 0.437500F, 0.500000F);
        Vector3f v38 = new Vector3f(-0.312500F, 0.000000F, 0.000000F);
        Vector3f v51 = new Vector3f(-0.250000F, 0.437500F, 0.500000F);
        Vector3f v20 = new Vector3f(0.312500F, 0.500000F, 0.250000F);
        Vector3f v10 = new Vector3f(0.250000F, 0.125000F, 0.250000F);
        Vector3f v49 = new Vector3f(-0.250000F, 0.500000F, 0.500000F);
        Vector3f v11 = new Vector3f(-0.250000F, 0.500000F, 0.187500F);
        Vector3f v50 = new Vector3f(-0.250000F, 0.437500F, 0.437500F);
        Vector3f v16 = new Vector3f(0.250000F, 0.125000F, -0.500000F);
        Vector3f v58 = new Vector3f(-0.312500F, 0.437500F, 0.250000F);
        Vector3f v57 = new Vector3f(-0.312500F, 0.500000F, 0.500000F);
        Vector3f v4 = new Vector3f(-0.250000F, 0.187500F, -0.437500F);
        Vector3f v30 = new Vector3f(0.375000F, 0.000000F, 0.000000F);
        Vector3f v26 = new Vector3f(-0.312500F, 0.125000F, -0.500000F);
        Vector3f v13 = new Vector3f(-0.250000F, 0.125000F, 0.250000F);
        Vector3f v6 = new Vector3f(-0.250000F, 0.125000F, -0.437500F);
        Vector3f v18 = new Vector3f(-0.250000F, 0.500000F, -0.437500F);
        Vector3f v5 = new Vector3f(-0.250000F, 0.187500F, 0.187500F);
        Vector3f v34 = new Vector3f(0.312500F, 0.000000F, -0.312500F);
        Vector3f v25 = new Vector3f(-0.312500F, 0.500000F, 0.250000F);
        Vec2 v143 = new Vec2(0.10938F, 0.50000F);
        Vec2 v80 = new Vec2(0.40625F, 0.09375F);
        Vec2 v95 = new Vec2(0.68750F, 0.37500F);
        Vec2 v116 = new Vec2(0.50000F, 0.71875F);
        Vec2 v161 = new Vec2(0.42188F, 0.03125F);
        Vec2 v104 = new Vec2(0.18750F, 0.90625F);
        Vec2 v90 = new Vec2(0.54688F, 0.37500F);
        Vec2 v103 = new Vec2(0.18750F, 0.71875F);
        Vec2 v120 = new Vec2(0.90625F, 0.90625F);
        Vec2 v168 = new Vec2(0.40625F, 0.06250F);
        Vec2 v155 = new Vec2(0.31250F, 0.34375F);
        Vec2 v149 = new Vec2(0.40625F, 0.50000F);
        Vec2 v94 = new Vec2(0.56250F, 0.37500F);
        Vec2 v185 = new Vec2(0.14062F, 0.28125F);
        Vec2 v88 = new Vec2(0.42188F, 0.34375F);
        Vec2 v122 = new Vec2(0.89062F, 0.71875F);
        Vec2 v150 = new Vec2(0.40625F, 0.65625F);
        Vec2 v157 = new Vec2(0.32812F, 0.50000F);
        Vec2 v111 = new Vec2(0.39062F, 0.90625F);
        Vec2 v153 = new Vec2(0.31250F, 0.65625F);
        Vec2 v84 = new Vec2(0.54688F, 0.06250F);
        Vec2 v166 = new Vec2(0.68750F, 0.03125F);
        Vec2 v137 = new Vec2(0.00000F, 0.65625F);
        Vec2 v107 = new Vec2(0.20312F, 0.34375F);
        Vec2 v78 = new Vec2(0.42188F, 0.28125F);
        Vec2 v134 = new Vec2(0.18750F, 0.50000F);
        Vec2 v86 = new Vec2(0.67188F, 0.09375F);
        Vec2 v102 = new Vec2(0.00000F, 0.71875F);
        Vec2 v92 = new Vec2(0.68750F, 0.56250F);
        Vec2 v146 = new Vec2(0.29688F, 0.65625F);
        Vec2 v160 = new Vec2(0.54688F, 0.03125F);
        Vec2 v63 = new Vec2(0.15625F, 0.31250F);
        Vec2 v81 = new Vec2(0.42188F, 0.09375F);
        Vec2 v135 = new Vec2(0.18750F, 0.65625F);
        Vec2 v106 = new Vec2(0.21875F, 0.34375F);
        Vec2 v85 = new Vec2(0.54688F, 0.28125F);
        Vec2 v152 = new Vec2(0.31250F, 0.50000F);
        Vec2 v176 = new Vec2(0.06250F, 0.15625F);
        Vec2 v127 = new Vec2(0.70312F, 0.34375F);
        Vec2 v142 = new Vec2(0.10938F, 0.34375F);
        Vec2 v118 = new Vec2(0.68750F, 0.90625F);
        Vec2 v164 = new Vec2(0.56250F, 0.06250F);
        Vec2 v83 = new Vec2(0.42188F, 0.06250F);
        Vec2 v145 = new Vec2(0.29688F, 0.50000F);
        Vec2 v132 = new Vec2(0.17188F, 0.65625F);
        Vec2 v110 = new Vec2(0.39062F, 0.71875F);
        Vec2 v151 = new Vec2(0.39062F, 0.65625F);
        Vec2 v140 = new Vec2(0.07812F, 0.34375F);
        Vec2 v181 = new Vec2(0.07812F, 0.00000F);
        Vec2 v179 = new Vec2(0.07812F, 0.15625F);
        Vec2 v75 = new Vec2(0.68750F, 0.09375F);
        Vec2 v97 = new Vec2(0.67188F, 0.37500F);
        Vec2 v147 = new Vec2(0.21875F, 0.65625F);
        Vec2 v128 = new Vec2(0.71875F, 0.71875F);
        Vec2 v148 = new Vec2(0.39062F, 0.50000F);
        Vec2 v131 = new Vec2(0.17188F, 0.50000F);
        Vec2 v69 = new Vec2(0.43750F, 0.34375F);
        Vec2 v98 = new Vec2(0.54688F, 0.56250F);
        Vec2 v121 = new Vec2(0.89062F, 0.90625F);
        Vec2 v141 = new Vec2(0.09375F, 0.34375F);
        Vec2 v73 = new Vec2(0.00000F, 0.34375F);
        Vec2 v64 = new Vec2(0.28125F, 0.34375F);
        Vec2 v119 = new Vec2(0.50000F, 0.90625F);
        Vec2 v144 = new Vec2(0.21875F, 0.50000F);
        Vec2 v71 = new Vec2(0.56250F, 0.31250F);
        Vec2 v167 = new Vec2(0.68750F, 0.06250F);
        Vec2 v136 = new Vec2(0.07812F, 0.65625F);
        Vec2 v62 = new Vec2(0.28125F, 0.31250F);
        Vec2 v186 = new Vec2(0.14062F, 0.31250F);
        Vec2 v77 = new Vec2(0.56250F, 0.28125F);
        Vec2 v68 = new Vec2(0.56250F, 0.34375F);
        Vec2 v191 = new Vec2(0.06250F, 0.31250F);
        Vec2 v113 = new Vec2(0.18750F, 0.34375F);
        Vec2 v193 = new Vec2(0.09375F, 0.15625F);
        Vec2 v65 = new Vec2(0.15625F, 0.34375F);
        Vec2 v192 = new Vec2(0.09375F, 0.28125F);
        Vec2 v190 = new Vec2(0.00000F, 0.28125F);
        Vec2 v189 = new Vec2(0.15625F, 0.28125F);
        Vec2 v188 = new Vec2(0.06250F, 0.28125F);
        Vec2 v187 = new Vec2(0.07812F, 0.31250F);
        Vec2 v100 = new Vec2(0.40625F, 0.37500F);
        Vec2 v184 = new Vec2(0.07812F, 0.28125F);
        Vec2 v158 = new Vec2(0.42188F, 0.00000F);
        Vec2 v178 = new Vec2(0.07812F, 0.12500F);
        Vec2 v183 = new Vec2(0.09375F, 0.00000F);
        Vec2 v101 = new Vec2(0.40625F, 0.56250F);
        Vec2 v105 = new Vec2(0.00000F, 0.90625F);
        Vec2 v172 = new Vec2(0.14062F, 0.12500F);
        Vec2 v70 = new Vec2(0.43750F, 0.31250F);
        Vec2 v180 = new Vec2(0.06250F, 0.00000F);
        Vec2 v82 = new Vec2(0.54688F, 0.09375F);
        Vec2 v177 = new Vec2(0.00000F, 0.15625F);
        Vec2 v182 = new Vec2(0.09375F, 0.12500F);
        Vec2 v125 = new Vec2(0.70312F, 0.71875F);
        Vec2 v91 = new Vec2(0.42188F, 0.37500F);
        Vec2 v67 = new Vec2(0.40625F, 0.31250F);
        Vec2 v173 = new Vec2(0.15625F, 0.12500F);
        Vec2 v175 = new Vec2(0.06250F, 0.12500F);
        Vec2 v79 = new Vec2(0.40625F, 0.28125F);
        Vec2 v171 = new Vec2(0.14062F, 0.15625F);
        Vec2 v170 = new Vec2(0.15625F, 0.15625F);
        Vec2 v112 = new Vec2(0.20312F, 0.90625F);
        Vec2 v124 = new Vec2(0.70312F, 0.90625F);
        Vec2 v169 = new Vec2(0.40625F, 0.03125F);
        Vec2 v117 = new Vec2(0.68750F, 0.71875F);
        Vec2 v114 = new Vec2(0.40625F, 0.71875F);
        Vec2 v165 = new Vec2(0.56250F, 0.03125F);
        Vec2 v93 = new Vec2(0.56250F, 0.56250F);
        Vec2 v76 = new Vec2(0.68750F, 0.28125F);
        Vec2 v60 = new Vec2(0.15625F, 0.00000F);
        Vec2 v115 = new Vec2(0.40625F, 0.90625F);
        Vec2 v163 = new Vec2(0.67188F, 0.03125F);
        Vec2 v162 = new Vec2(0.67188F, 0.00000F);
        Vec2 v74 = new Vec2(0.56250F, 0.09375F);
        Vec2 v126 = new Vec2(0.71875F, 0.34375F);
        Vec2 v96 = new Vec2(0.67188F, 0.34375F);
        Vec2 v109 = new Vec2(0.21875F, 0.71875F);
        Vec2 v138 = new Vec2(0.00000F, 0.50000F);
        Vec2 v156 = new Vec2(0.32812F, 0.34375F);
        Vec2 v89 = new Vec2(0.54688F, 0.34375F);
        Vec2 v154 = new Vec2(0.29688F, 0.34375F);
        Vec2 v159 = new Vec2(0.54688F, 0.00000F);
        Vec2 v72 = new Vec2(0.00000F, 0.31250F);
        Vec2 v66 = new Vec2(0.40625F, 0.00000F);
        Vec2 v174 = new Vec2(0.00000F, 0.12500F);
        Vec2 v133 = new Vec2(0.09375F, 0.65625F);
        Vec2 v87 = new Vec2(0.67188F, 0.06250F);
        Vec2 v61 = new Vec2(0.28125F, 0.00000F);
        Vec2 v99 = new Vec2(0.42188F, 0.56250F);
        Vec2 v130 = new Vec2(0.09375F, 0.50000F);
        Vec2 v129 = new Vec2(0.68750F, 0.34375F);
        Vec2 v139 = new Vec2(0.07812F, 0.50000F);
        Vec2 v123 = new Vec2(0.90625F, 0.71875F);
        Vec2 v108 = new Vec2(0.20312F, 0.71875F);
        ImmutableList.Builder<SimpleQuad> builder;

        builder = ImmutableList.builder();

        // Cart_down
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v1, c0, v63, v194, v4, c0, v62, v194, v5, c0, v61, v194, v0, c0, v60, v194));
        builder.add(new SimpleQuad(v3, c0, v65, v196, v6, c0, v64, v196, v4, c0, v62, v196, v1, c0, v63, v196));
        builder.add(new SimpleQuad(v6, c0, v67, v195, v3, c0, v62, v195, v2, c0, v61, v195, v7, c0, v66, v195));
        builder.add(new SimpleQuad(v0, c0, v71, v197, v5, c0, v70, v197, v7, c0, v69, v197, v2, c0, v68, v197));
        builder.add(new SimpleQuad(v2, c0, v73, v198, v3, c0, v65, v198, v1, c0, v63, v198, v0, c0, v72, v198));
        builder.add(new SimpleQuad(v6, c0, v64, v199, v7, c0, v69, v199, v5, c0, v70, v199, v4, c0, v62, v199));
        // CART_DOWN_QUADS = builder.build();

        // Cart_front
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v13, c1, v77, v197, v10, c1, v76, v197, v8, c1, v75, v197, v12, c1, v74, v197));
        builder.add(new SimpleQuad(v9, c1, v81, v198, v8, c1, v80, v198, v10, c1, v79, v198, v2, c1, v78, v198));
        builder.add(new SimpleQuad(v12, c1, v84, v194, v8, c1, v83, v194, v9, c1, v81, v194, v11, c1, v82, v194));
        builder.add(new SimpleQuad(v12, c1, v74, v199, v11, c1, v82, v199, v7, c1, v85, v199, v13, c1, v77, v199));
        builder.add(new SimpleQuad(v10, c1, v84, v195, v13, c1, v87, v195, v7, c1, v86, v195, v2, c1, v82, v195));
        builder.add(new SimpleQuad(v11, c1, v82, v196, v9, c1, v81, v196, v2, c1, v78, v196, v7, c1, v85, v196));
        // CART_FRONT_QUADS = builder.build();

        // Cart_back
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v15, c2, v91, v194, v17, c2, v90, v194, v18, c2, v89, v194, v14, c2, v88, v194));
        builder.add(new SimpleQuad(v14, c2, v95, v197, v18, c2, v94, v197, v6, c2, v93, v197, v3, c2, v92, v197));
        builder.add(new SimpleQuad(v19, c2, v97, v195, v16, c2, v90, v195, v3, c2, v89, v195, v6, c2, v96, v195));
        builder.add(new SimpleQuad(v16, c2, v99, v196, v19, c2, v98, v196, v17, c2, v90, v196, v15, c2, v91, v196));
        builder.add(new SimpleQuad(v3, c2, v101, v198, v16, c2, v99, v198, v15, c2, v91, v198, v14, c2, v100, v198));
        builder.add(new SimpleQuad(v19, c2, v98, v199, v6, c2, v93, v199, v18, c2, v94, v199, v17, c2, v90, v199));
        // CART_BACK_QUADS = builder.build();

        // Cart_left
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v22, c3, v105, v198, v23, c3, v104, v198, v21, c3, v103, v198, v20, c3, v102, v198));
        builder.add(new SimpleQuad(v16, c3, v109, v195, v23, c3, v108, v195, v22, c3, v107, v195, v10, c3, v106, v195));
        builder.add(new SimpleQuad(v16, c3, v112, v199, v10, c3, v111, v199, v8, c3, v110, v199, v15, c3, v108, v199));
        builder.add(new SimpleQuad(v8, c3, v107, v194, v20, c3, v113, v194, v21, c3, v103, v194, v15, c3, v108, v194));
        builder.add(new SimpleQuad(v10, c3, v111, v197, v22, c3, v115, v197, v20, c3, v114, v197, v8, c3, v110, v197));
        builder.add(new SimpleQuad(v15, c3, v108, v196, v21, c3, v103, v196, v23, c3, v104, v196, v16, c3, v112, v196));
        // CART_LEFT_QUADS = builder.build();

        // Cart_right
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v13, c4, v119, v198, v19, c4, v118, v198, v17, c4, v117, v198, v12, c4, v116, v198));
        builder.add(new SimpleQuad(v12, c4, v123, v197, v25, c4, v122, v197, v27, c4, v121, v197, v13, c4, v120, v197));
        builder.add(new SimpleQuad(v25, c4, v122, v199, v24, c4, v125, v199, v26, c4, v124, v199, v27, c4, v121, v199));
        builder.add(new SimpleQuad(v26, c4, v128, v195, v19, c4, v125, v195, v13, c4, v127, v195, v27, c4, v126, v195));
        builder.add(new SimpleQuad(v25, c4, v127, v194, v12, c4, v129, v194, v17, c4, v117, v194, v24, c4, v125, v194));
        builder.add(new SimpleQuad(v24, c4, v125, v196, v17, c4, v117, v196, v19, c4, v118, v196, v26, c4, v124, v196));
        // CART_RIGHT_QUADS = builder.build();

        // Wheel_left
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v34, c5, v133, v199, v35, c5, v132, v199, v33, c5, v131, v199, v32, c5, v130, v199));
        builder.add(new SimpleQuad(v35, c5, v132, v197, v30, c5, v135, v197, v28, c5, v134, v197, v33, c5, v131, v197));
        builder.add(new SimpleQuad(v29, c5, v139, v198, v28, c5, v138, v198, v30, c5, v137, v198, v31, c5, v136, v198));
        builder.add(new SimpleQuad(v31, c5, v136, v196, v34, c5, v133, v196, v32, c5, v130, v196, v29, c5, v139, v196));
        builder.add(new SimpleQuad(v33, c5, v141, v194, v28, c5, v140, v194, v29, c5, v139, v194, v32, c5, v130, v194));
        builder.add(new SimpleQuad(v34, c5, v143, v195, v31, c5, v130, v195, v30, c5, v141, v195, v35, c5, v142, v195));
        // WHEEL_LEFT_QUADS = builder.build();

        // Wheel_right
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v38, c6, v147, v198, v39, c6, v146, v198, v37, c6, v145, v198, v36, c6, v144, v198));
        builder.add(new SimpleQuad(v43, c6, v151, v197, v38, c6, v150, v197, v36, c6, v149, v197, v41, c6, v148, v197));
        builder.add(new SimpleQuad(v42, c6, v153, v199, v43, c6, v151, v199, v41, c6, v148, v199, v40, c6, v152, v199));
        builder.add(new SimpleQuad(v39, c6, v146, v196, v42, c6, v153, v196, v40, c6, v152, v196, v37, c6, v145, v196));
        builder.add(new SimpleQuad(v41, c6, v155, v194, v36, c6, v154, v194, v37, c6, v145, v194, v40, c6, v152, v194));
        builder.add(new SimpleQuad(v42, c6, v157, v195, v39, c6, v152, v195, v38, c6, v155, v195, v43, c6, v156, v195));
        // WHEEL_RIGHT_QUADS = builder.build();

        // Handle_bar
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v45, c7, v161, v194, v48, c7, v160, v194, v49, c7, v159, v194, v44, c7, v158, v194));
        builder.add(new SimpleQuad(v48, c7, v160, v196, v45, c7, v161, v196, v47, c7, v83, v196, v50, c7, v84, v196));
        builder.add(new SimpleQuad(v50, c7, v163, v195, v47, c7, v160, v195, v46, c7, v159, v195, v51, c7, v162, v195));
        builder.add(new SimpleQuad(v49, c7, v165, v199, v48, c7, v160, v199, v50, c7, v84, v199, v51, c7, v164, v199));
        builder.add(new SimpleQuad(v51, c7, v164, v197, v46, c7, v167, v197, v44, c7, v166, v197, v49, c7, v165, v197));
        builder.add(new SimpleQuad(v45, c7, v161, v198, v44, c7, v169, v198, v46, c7, v168, v198, v47, c7, v83, v198));
        // HANDLE_BAR_QUADS = builder.build();

        // Handle_left
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v52, c8, v173, v197, v44, c8, v172, v197, v46, c8, v171, v197, v53, c8, v170, v197));
        builder.add(new SimpleQuad(v53, c8, v177, v198, v54, c8, v176, v198, v20, c8, v175, v198, v52, c8, v174, v198));
        builder.add(new SimpleQuad(v54, c8, v176, v196, v55, c8, v179, v196, v8, c8, v178, v196, v20, c8, v175, v196));
        builder.add(new SimpleQuad(v44, c8, v181, v194, v52, c8, v180, v194, v20, c8, v175, v194, v8, c8, v178, v194));
        builder.add(new SimpleQuad(v44, c8, v172, v199, v8, c8, v178, v199, v55, c8, v179, v199, v46, c8, v171, v199));
        builder.add(new SimpleQuad(v53, c8, v181, v195, v46, c8, v183, v195, v55, c8, v182, v195, v54, c8, v178, v195));
        // HANDLE_LEFT_QUADS = builder.build();

        // Handle_right
        // builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v58, c9, v187, v199, v59, c9, v186, v199, v57, c9, v185, v199, v25, c9, v184, v199));
        builder.add(new SimpleQuad(v12, c9, v188, v194, v25, c9, v184, v194, v57, c9, v179, v194, v49, c9, v176, v194));
        builder.add(new SimpleQuad(v59, c9, v186, v197, v51, c9, v63, v197, v49, c9, v189, v197, v57, c9, v185, v197));
        builder.add(new SimpleQuad(v51, c9, v72, v198, v56, c9, v191, v198, v12, c9, v188, v198, v49, c9, v190, v198));
        builder.add(new SimpleQuad(v51, c9, v179, v195, v59, c9, v193, v195, v58, c9, v192, v195, v56, c9, v184, v195));
        builder.add(new SimpleQuad(v25, c9, v184, v196, v12, c9, v188, v196, v56, c9, v191, v196, v58, c9, v187, v196));
        // HANDLE_RIGHT_QUADS = builder.build();

        HANDCART_QUADS = builder.build();
    }
}
