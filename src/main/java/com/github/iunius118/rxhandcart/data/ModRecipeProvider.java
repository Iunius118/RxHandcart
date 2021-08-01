package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider  extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ResourceLocation handcartRegistryName = ModItems.HANDCART.getRegistryName();
        ShapedRecipeBuilder.shaped(ModItems.HANDCART)
                .group(handcartRegistryName.toString())
                .pattern(" P ")
                .pattern("sBP")
                .pattern(" s ")
                .define('P', ItemTags.PLANKS)
                .define('s', Tags.Items.RODS_WOODEN)
                .define('B', Blocks.BARREL)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer, handcartRegistryName);

        ResourceLocation handcartSettingRegistryName = ModItems.HANDCART_SETTING.getRegistryName();
        ShapelessRecipeBuilder.shapeless(ModItems.HANDCART_SETTING)
                .group(handcartSettingRegistryName.toString())
                .requires(ModItems.HANDCART)
                .unlockedBy("has_handcart", has(ModItems.HANDCART))
                .save(consumer, handcartSettingRegistryName);

        ShapelessRecipeBuilder.shapeless(ModItems.HANDCART)
                .group(handcartRegistryName.toString())
                .requires(ModItems.HANDCART_SETTING)
                .unlockedBy("has_handcart", has(ModItems.HANDCART))
                .save(consumer, handcartRegistryName + "_res");
    }
}
