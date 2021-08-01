package com.github.iunius118.rxhandcart.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class HandcartModel extends SimpleModel implements IHandcartModel {
    public static final List<SimpleQuad> HANDCART_QUADS;

    @Override
    public void render(PoseStack matrixStack, VertexConsumer vertexBuilder, int lightmapCoord, int overlayColor, float red, float green, float blue, float alpha) {
        renderQuads(matrixStack, vertexBuilder, HANDCART_QUADS, lightmapCoord, overlayColor,red,green,blue,alpha);
    }

    static {
        Vector4f c0 = new Vector4f(1F, 1F, 1F, 1F); // Handcart color

        Vector3f v121 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v49 = new Vector3f(-0.250000F, 0.437500F, 0.625000F);
        Vector3f v123 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v23 = new Vector3f(0.312500F, 0.500000F, 0.375000F);
        Vector3f v47 = new Vector3f(-0.375000F, 0.156250F, -0.031250F);
        Vector3f v42 = new Vector3f(-0.312500F, 0.000000F, 0.125000F);
        Vector3f v25 = new Vector3f(-0.312500F, 0.500000F, -0.375000F);
        Vector3f v48 = new Vector3f(-0.250000F, 0.500000F, 0.625000F);
        Vector3f v43 = new Vector3f(-0.312500F, 0.312500F, 0.125000F);
        Vector3f v53 = new Vector3f(0.250000F, 0.437500F, 0.562500F);
        Vector3f v40 = new Vector3f(-0.375000F, 0.312500F, -0.187500F);
        Vector3f v2 = new Vector3f(0.250000F, 0.187500F, 0.312500F);
        Vector3f v0 = new Vector3f(-0.250000F, 0.187500F, -0.312500F);
        Vector3f v17 = new Vector3f(0.250000F, 0.125000F, -0.375000F);
        Vector3f v19 = new Vector3f(-0.250000F, 0.500000F, -0.375000F);
        Vector3f v6 = new Vector3f(0.250000F, 0.125000F, -0.312500F);
        Vector3f v31 = new Vector3f(0.375000F, 0.000000F, -0.187500F);
        Vector3f v41 = new Vector3f(-0.375000F, 0.000000F, -0.187500F);
        Vector3f v21 = new Vector3f(0.312500F, 0.125000F, -0.375000F);
        Vector3f v5 = new Vector3f(0.250000F, 0.125000F, 0.312500F);
        Vector3f v30 = new Vector3f(0.375000F, 0.312500F, -0.187500F);
        Vector3f v35 = new Vector3f(0.375000F, 0.000000F, 0.125000F);
        Vector3f v12 = new Vector3f(-0.250000F, 0.500000F, 0.375000F);
        Vector3f v15 = new Vector3f(-0.250000F, 0.500000F, -0.312500F);
        Vector3f v36 = new Vector3f(0.312500F, 0.156250F, -0.031250F);
        Vector3f v124 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v7 = new Vector3f(-0.250000F, 0.125000F, -0.312500F);
        Vector3f v4 = new Vector3f(-0.250000F, 0.125000F, 0.312500F);
        Vector3f v46 = new Vector3f(-0.312500F, 0.156250F, -0.031250F);
        Vector3f v9 = new Vector3f(-0.250000F, 0.500000F, 0.312500F);
        Vector3f v119 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v34 = new Vector3f(0.375000F, 0.312500F, 0.125000F);
        Vector3f v18 = new Vector3f(-0.250000F, 0.125000F, -0.375000F);
        Vector3f v20 = new Vector3f(0.312500F, 0.500000F, -0.375000F);
        Vector3f v50 = new Vector3f(-0.250000F, 0.437500F, 0.562500F);
        Vector3f v32 = new Vector3f(0.312500F, 0.000000F, 0.125000F);
        Vector3f v22 = new Vector3f(0.312500F, 0.125000F, 0.375000F);
        Vector3f v56 = new Vector3f(0.312500F, 0.500000F, 0.625000F);
        Vector3f v39 = new Vector3f(-0.312500F, 0.312500F, -0.187500F);
        Vector3f v3 = new Vector3f(-0.250000F, 0.187500F, 0.312500F);
        Vector3f v33 = new Vector3f(0.312500F, 0.312500F, 0.125000F);
        Vector3f v11 = new Vector3f(0.250000F, 0.500000F, 0.375000F);
        Vector3f v44 = new Vector3f(-0.375000F, 0.312500F, 0.125000F);
        Vector3f v28 = new Vector3f(0.312500F, 0.000000F, -0.187500F);
        Vector3f v63 = new Vector3f(-0.250000F, 0.437500F, 0.375000F);
        Vector3f v38 = new Vector3f(-0.312500F, 0.000000F, -0.187500F);
        Vector3f v14 = new Vector3f(0.250000F, 0.500000F, -0.312500F);
        Vector3f v24 = new Vector3f(-0.312500F, 0.125000F, -0.375000F);
        Vector3f v26 = new Vector3f(-0.312500F, 0.500000F, 0.375000F);
        Vector3f v55 = new Vector3f(0.250000F, 0.500000F, 0.562500F);
        Vector3f v51 = new Vector3f(-0.250000F, 0.500000F, 0.562500F);
        Vector3f v122 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v59 = new Vector3f(0.250000F, 0.437500F, 0.375000F);
        Vector3f v62 = new Vector3f(-0.312500F, 0.437500F, 0.375000F);
        Vector3f v61 = new Vector3f(-0.312500F, 0.500000F, 0.625000F);
        Vector3f v27 = new Vector3f(-0.312500F, 0.125000F, 0.375000F);
        Vector3f v1 = new Vector3f(0.250000F, 0.187500F, -0.312500F);
        Vector3f v60 = new Vector3f(-0.312500F, 0.437500F, 0.625000F);
        Vector3f v120 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v16 = new Vector3f(0.250000F, 0.500000F, -0.375000F);
        Vector3f v10 = new Vector3f(0.250000F, 0.125000F, 0.375000F);
        Vector3f v58 = new Vector3f(0.312500F, 0.437500F, 0.375000F);
        Vector3f v57 = new Vector3f(0.312500F, 0.437500F, 0.625000F);
        Vector3f v37 = new Vector3f(0.375000F, 0.156250F, -0.031250F);
        Vector3f v54 = new Vector3f(0.250000F, 0.500000F, 0.625000F);
        Vector3f v52 = new Vector3f(0.250000F, 0.437500F, 0.625000F);
        Vector3f v13 = new Vector3f(-0.250000F, 0.125000F, 0.375000F);
        Vector3f v8 = new Vector3f(0.250000F, 0.500000F, 0.312500F);
        Vector3f v45 = new Vector3f(-0.375000F, 0.000000F, 0.125000F);
        Vector3f v29 = new Vector3f(0.312500F, 0.312500F, -0.187500F);
        Vec2 v76 = new Vec2(0.87500F, 0.25000F);
        Vec2 v92 = new Vec2(0.00000F, 0.18750F);
        Vec2 v77 = new Vec2(1.00000F, 0.37500F);
        Vec2 v112 = new Vec2(0.68750F, 0.68750F);
        Vec2 v66 = new Vec2(1.00000F, 0.00000F);
        Vec2 v84 = new Vec2(0.18750F, 0.93750F);
        Vec2 v80 = new Vec2(1.00000F, 0.68750F);
        Vec2 v75 = new Vec2(1.00000F, 0.25000F);
        Vec2 v65 = new Vec2(0.00000F, 0.00000F);
        Vec2 v79 = new Vec2(0.00000F, 0.68750F);
        Vec2 v73 = new Vec2(0.00000F, 0.81250F);
        Vec2 v72 = new Vec2(1.00000F, 0.81250F);
        Vec2 v95 = new Vec2(0.00000F, 0.50000F);
        Vec2 v98 = new Vec2(0.12500F, 0.18750F);
        Vec2 v94 = new Vec2(0.75000F, 0.18750F);
        Vec2 v100 = new Vec2(0.87500F, 0.18750F);
        Vec2 v104 = new Vec2(0.12500F, 0.50000F);
        Vec2 v82 = new Vec2(0.12500F, 0.25000F);
        Vec2 v91 = new Vec2(0.12500F, 0.37500F);
        Vec2 v111 = new Vec2(0.68750F, 0.50000F);
        Vec2 v118 = new Vec2(0.37500F, 0.68750F);
        Vec2 v64 = new Vec2(0.00000F, 0.93750F);
        Vec2 v67 = new Vec2(1.00000F, 0.93750F);
        Vec2 v115 = new Vec2(0.37500F, 0.00000F);
        Vec2 v68 = new Vec2(1.00000F, 0.12500F);
        Vec2 v109 = new Vec2(0.68750F, 0.18750F);
        Vec2 v114 = new Vec2(0.18750F, 0.50000F);
        Vec2 v89 = new Vec2(0.78125F, 0.21875F);
        Vec2 v113 = new Vec2(0.18750F, 0.68750F);
        Vec2 v69 = new Vec2(0.00000F, 0.12500F);
        Vec2 v110 = new Vec2(0.18750F, 0.18750F);
        Vec2 v90 = new Vec2(1.00000F, 0.43750F);
        Vec2 v88 = new Vec2(0.00000F, 0.43750F);
        Vec2 v105 = new Vec2(0.87500F, 0.56250F);
        Vec2 v108 = new Vec2(0.68750F, 0.00000F);
        Vec2 v99 = new Vec2(0.12500F, 0.00000F);
        Vec2 v85 = new Vec2(0.75000F, 0.93750F);
        Vec2 v102 = new Vec2(0.87500F, 0.68750F);
        Vec2 v106 = new Vec2(1.00000F, 0.56250F);
        Vec2 v103 = new Vec2(0.87500F, 0.50000F);
        Vec2 v116 = new Vec2(0.37500F, 0.18750F);
        Vec2 v70 = new Vec2(0.87500F, 0.93750F);
        Vec2 v107 = new Vec2(0.18750F, 0.00000F);
        Vec2 v97 = new Vec2(0.75000F, 0.68750F);
        Vec2 v81 = new Vec2(0.12500F, 0.93750F);
        Vec2 v96 = new Vec2(0.75000F, 0.50000F);
        Vec2 v93 = new Vec2(0.75000F, 0.00000F);
        Vec2 v78 = new Vec2(0.00000F, 0.37500F);
        Vec2 v101 = new Vec2(0.12500F, 0.68750F);
        Vec2 v71 = new Vec2(0.87500F, 0.00000F);
        Vec2 v74 = new Vec2(0.00000F, 0.25000F);
        Vec2 v117 = new Vec2(0.37500F, 0.50000F);
        Vec2 v83 = new Vec2(0.18750F, 1.00000F);
        Vec2 v87 = new Vec2(0.21875F, 0.21875F);
        Vec2 v86 = new Vec2(0.75000F, 1.00000F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Handcart
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v67, v119, v2, c0, v66, v119, v1, c0, v65, v119, v0, c0, v64, v119));
        builder.add(new SimpleQuad(v7, c0, v67, v120, v6, c0, v66, v120, v5, c0, v65, v120, v4, c0, v64, v120));
        builder.add(new SimpleQuad(v5, c0, v66, v121, v6, c0, v65, v121, v1, c0, v69, v121, v2, c0, v68, v121));
        builder.add(new SimpleQuad(v1, c0, v71, v122, v6, c0, v66, v122, v7, c0, v67, v122, v0, c0, v70, v122));
        builder.add(new SimpleQuad(v4, c0, v70, v123, v5, c0, v71, v123, v2, c0, v66, v123, v3, c0, v67, v123));
        builder.add(new SimpleQuad(v7, c0, v73, v124, v4, c0, v72, v124, v3, c0, v67, v124, v0, c0, v64, v124));
        builder.add(new SimpleQuad(v4, c0, v67, v122, v9, c0, v75, v122, v8, c0, v74, v122, v5, c0, v64, v122));
        builder.add(new SimpleQuad(v11, c0, v74, v123, v12, c0, v75, v123, v13, c0, v67, v123, v10, c0, v64, v123));
        builder.add(new SimpleQuad(v8, c0, v76, v121, v11, c0, v75, v121, v10, c0, v67, v121, v5, c0, v70, v121));
        builder.add(new SimpleQuad(v13, c0, v70, v124, v12, c0, v76, v124, v9, c0, v75, v124, v4, c0, v67, v124));
        builder.add(new SimpleQuad(v10, c0, v73, v120, v13, c0, v72, v120, v4, c0, v67, v120, v5, c0, v64, v120));
        builder.add(new SimpleQuad(v12, c0, v75, v119, v11, c0, v74, v119, v8, c0, v78, v119, v9, c0, v77, v119));
        builder.add(new SimpleQuad(v15, c0, v67, v123, v7, c0, v75, v123, v6, c0, v74, v123, v14, c0, v64, v123));
        builder.add(new SimpleQuad(v17, c0, v74, v122, v18, c0, v75, v122, v19, c0, v67, v122, v16, c0, v64, v122));
        builder.add(new SimpleQuad(v6, c0, v76, v121, v17, c0, v75, v121, v16, c0, v67, v121, v14, c0, v70, v121));
        builder.add(new SimpleQuad(v19, c0, v70, v124, v18, c0, v76, v124, v7, c0, v75, v124, v15, c0, v67, v124));
        builder.add(new SimpleQuad(v16, c0, v73, v119, v19, c0, v72, v119, v15, c0, v67, v119, v14, c0, v64, v119));
        builder.add(new SimpleQuad(v18, c0, v75, v120, v17, c0, v74, v120, v6, c0, v78, v120, v7, c0, v77, v120));
        builder.add(new SimpleQuad(v23, c0, v80, v121, v22, c0, v66, v121, v21, c0, v65, v121, v20, c0, v79, v121));
        builder.add(new SimpleQuad(v17, c0, v65, v124, v10, c0, v66, v124, v11, c0, v80, v124, v16, c0, v79, v124));
        builder.add(new SimpleQuad(v21, c0, v76, v122, v17, c0, v75, v122, v16, c0, v67, v122, v20, c0, v70, v122));
        builder.add(new SimpleQuad(v11, c0, v70, v123, v10, c0, v76, v123, v22, c0, v75, v123, v23, c0, v67, v123));
        builder.add(new SimpleQuad(v16, c0, v73, v119, v11, c0, v72, v119, v23, c0, v67, v119, v20, c0, v64, v119));
        builder.add(new SimpleQuad(v10, c0, v75, v120, v17, c0, v74, v120, v21, c0, v78, v120, v22, c0, v77, v120));
        builder.add(new SimpleQuad(v27, c0, v80, v124, v26, c0, v66, v124, v25, c0, v65, v124, v24, c0, v79, v124));
        builder.add(new SimpleQuad(v19, c0, v65, v121, v12, c0, v66, v121, v13, c0, v80, v121, v18, c0, v79, v121));
        builder.add(new SimpleQuad(v18, c0, v73, v120, v13, c0, v72, v120, v27, c0, v67, v120, v24, c0, v64, v120));
        builder.add(new SimpleQuad(v12, c0, v75, v119, v19, c0, v74, v119, v25, c0, v78, v119, v26, c0, v77, v119));
        builder.add(new SimpleQuad(v13, c0, v64, v123, v12, c0, v74, v123, v26, c0, v82, v123, v27, c0, v81, v123));
        builder.add(new SimpleQuad(v25, c0, v74, v122, v19, c0, v82, v122, v18, c0, v81, v122, v24, c0, v64, v122));
        builder.add(new SimpleQuad(v29, c0, v86, v122, v30, c0, v85, v122, v31, c0, v84, v122, v28, c0, v83, v122));
        builder.add(new SimpleQuad(v35, c0, v83, v123, v34, c0, v86, v123, v33, c0, v85, v123, v32, c0, v84, v123));
        builder.add(new SimpleQuad(v34, c0, v85, v119, v30, c0, v84, v119, v29, c0, v83, v119, v33, c0, v86, v119));
        builder.add(new SimpleQuad(v31, c0, v83, v120, v35, c0, v86, v120, v32, c0, v85, v120, v28, c0, v84, v120));
        builder.add(new SimpleQuad(v33, c0, v65, v124, v29, c0, v88, v124, v36, c0, v87, v124, v36, c0, v87, v124));
        builder.add(new SimpleQuad(v29, c0, v88, v124, v28, c0, v65, v124, v36, c0, v87, v124, v36, c0, v87, v124));
        builder.add(new SimpleQuad(v28, c0, v65, v124, v32, c0, v88, v124, v36, c0, v87, v124, v36, c0, v87, v124));
        builder.add(new SimpleQuad(v32, c0, v88, v124, v33, c0, v65, v124, v36, c0, v87, v124, v36, c0, v87, v124));
        builder.add(new SimpleQuad(v30, c0, v90, v121, v34, c0, v66, v121, v37, c0, v89, v121, v37, c0, v89, v121));
        builder.add(new SimpleQuad(v31, c0, v66, v121, v30, c0, v90, v121, v37, c0, v89, v121, v37, c0, v89, v121));
        builder.add(new SimpleQuad(v35, c0, v90, v121, v31, c0, v66, v121, v37, c0, v89, v121, v37, c0, v89, v121));
        builder.add(new SimpleQuad(v34, c0, v66, v121, v35, c0, v90, v121, v37, c0, v89, v121, v37, c0, v89, v121));
        builder.add(new SimpleQuad(v38, c0, v83, v122, v41, c0, v84, v122, v40, c0, v85, v122, v39, c0, v86, v122));
        builder.add(new SimpleQuad(v42, c0, v84, v123, v43, c0, v85, v123, v44, c0, v86, v123, v45, c0, v83, v123));
        builder.add(new SimpleQuad(v43, c0, v86, v119, v39, c0, v83, v119, v40, c0, v84, v119, v44, c0, v85, v119));
        builder.add(new SimpleQuad(v38, c0, v84, v120, v42, c0, v85, v120, v45, c0, v86, v120, v41, c0, v83, v120));
        builder.add(new SimpleQuad(v46, c0, v87, v121, v39, c0, v88, v121, v43, c0, v65, v121, v43, c0, v65, v121));
        builder.add(new SimpleQuad(v46, c0, v87, v121, v38, c0, v65, v121, v39, c0, v88, v121, v39, c0, v88, v121));
        builder.add(new SimpleQuad(v46, c0, v87, v121, v42, c0, v88, v121, v38, c0, v65, v121, v38, c0, v65, v121));
        builder.add(new SimpleQuad(v46, c0, v87, v121, v43, c0, v65, v121, v42, c0, v88, v121, v42, c0, v88, v121));
        builder.add(new SimpleQuad(v47, c0, v89, v124, v44, c0, v66, v124, v40, c0, v90, v124, v40, c0, v90, v124));
        builder.add(new SimpleQuad(v47, c0, v89, v124, v40, c0, v90, v124, v41, c0, v66, v124, v41, c0, v66, v124));
        builder.add(new SimpleQuad(v47, c0, v89, v124, v41, c0, v66, v124, v45, c0, v90, v124, v45, c0, v90, v124));
        builder.add(new SimpleQuad(v47, c0, v89, v124, v45, c0, v90, v124, v44, c0, v66, v124, v44, c0, v66, v124));
        builder.add(new SimpleQuad(v50, c0, v91, v124, v49, c0, v82, v124, v48, c0, v74, v124, v51, c0, v78, v124));
        builder.add(new SimpleQuad(v53, c0, v94, v120, v52, c0, v93, v120, v49, c0, v65, v120, v50, c0, v92, v120));
        builder.add(new SimpleQuad(v49, c0, v79, v123, v52, c0, v97, v123, v54, c0, v96, v123, v48, c0, v95, v123));
        builder.add(new SimpleQuad(v51, c0, v100, v119, v48, c0, v71, v119, v54, c0, v99, v119, v55, c0, v98, v119));
        builder.add(new SimpleQuad(v53, c0, v104, v122, v50, c0, v103, v122, v51, c0, v102, v122, v55, c0, v101, v122));
        builder.add(new SimpleQuad(v55, c0, v80, v121, v54, c0, v106, v121, v52, c0, v105, v121, v53, c0, v102, v121));
        builder.add(new SimpleQuad(v52, c0, v78, v123, v57, c0, v91, v123, v56, c0, v82, v123, v54, c0, v74, v123));
        builder.add(new SimpleQuad(v57, c0, v110, v121, v58, c0, v109, v121, v23, c0, v108, v121, v56, c0, v107, v121));
        builder.add(new SimpleQuad(v54, c0, v114, v119, v56, c0, v113, v119, v23, c0, v112, v119, v11, c0, v111, v119));
        builder.add(new SimpleQuad(v59, c0, v116, v124, v52, c0, v100, v124, v54, c0, v71, v124, v11, c0, v115, v124));
        builder.add(new SimpleQuad(v59, c0, v118, v120, v58, c0, v117, v120, v57, c0, v103, v120, v52, c0, v102, v120));
        builder.add(new SimpleQuad(v58, c0, v102, v122, v59, c0, v80, v122, v11, c0, v106, v122, v23, c0, v105, v122));
        builder.add(new SimpleQuad(v61, c0, v91, v123, v60, c0, v82, v123, v49, c0, v74, v123, v48, c0, v78, v123));
        builder.add(new SimpleQuad(v26, c0, v109, v124, v62, c0, v108, v124, v60, c0, v107, v124, v61, c0, v110, v124));
        builder.add(new SimpleQuad(v60, c0, v113, v120, v62, c0, v112, v120, v63, c0, v111, v120, v49, c0, v114, v120));
        builder.add(new SimpleQuad(v48, c0, v100, v121, v49, c0, v71, v121, v63, c0, v115, v121, v12, c0, v116, v121));
        builder.add(new SimpleQuad(v26, c0, v117, v119, v61, c0, v103, v119, v48, c0, v102, v119, v12, c0, v118, v119));
        builder.add(new SimpleQuad(v12, c0, v80, v122, v63, c0, v106, v122, v62, c0, v105, v122, v26, c0, v102, v122));
        HANDCART_QUADS = builder.build();
    }
}
