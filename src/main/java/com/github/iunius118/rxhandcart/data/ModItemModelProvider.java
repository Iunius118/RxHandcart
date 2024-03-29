package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.world.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, RxHandcart.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerSingleTextureModel(ModItems.HANDCART);
        registerSingleTextureModel(ModItems.HANDCART_SETTING);
    }

    private void registerSingleTextureModel(Item item) {
        String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
        singleTexture(itemPath, mcLoc("item/generated"), "layer0", modLoc("item/" + itemPath));
    }
}
