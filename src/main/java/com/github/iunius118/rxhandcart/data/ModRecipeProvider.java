package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider  extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.HANDCART)
                .pattern(" P ")
                .pattern("sBP")
                .pattern(" s ")
                .define('P', ItemTags.PLANKS)
                .define('s', Tags.Items.RODS_WOODEN)
                .define('B', Blocks.BARREL)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer, ModItems.HANDCART.getRegistryName());
    }
}
