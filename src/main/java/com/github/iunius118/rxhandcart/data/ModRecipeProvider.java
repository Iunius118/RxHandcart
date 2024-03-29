package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.world.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ResourceLocation handcartRegistryName = getItemId(ModItems.HANDCART);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HANDCART)
                .group(handcartRegistryName.toString())
                .pattern(" P ")
                .pattern("sBP")
                .pattern(" s ")
                .define('P', ItemTags.PLANKS)
                .define('s', Tags.Items.RODS_WOODEN)
                .define('B', Blocks.BARREL)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer, handcartRegistryName);

        ResourceLocation handcartSettingRegistryName = getItemId(ModItems.HANDCART_SETTING);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HANDCART_SETTING)
                .group(handcartSettingRegistryName.toString())
                .requires(ModItems.HANDCART)
                .unlockedBy("has_handcart", has(ModItems.HANDCART))
                .save(consumer, handcartSettingRegistryName);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HANDCART)
                .group(handcartRegistryName.toString())
                .requires(ModItems.HANDCART_SETTING)
                .unlockedBy("has_handcart", has(ModItems.HANDCART))
                .save(consumer, handcartRegistryName + "_res");
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
