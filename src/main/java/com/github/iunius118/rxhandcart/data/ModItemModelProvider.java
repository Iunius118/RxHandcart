package com.github.iunius118.rxhandcart.data;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, RxHandcart.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerSingleTextureModel(ModItems.HANDCART);
    }

    private void registerSingleTextureModel(Item item) {
        if (item == null) return;
        if (item.getRegistryName() == null) return;

        String itemPath = item.getRegistryName().getPath();
        singleTexture(itemPath, mcLoc("item/generated"), "layer0", modLoc("item/" + itemPath));
    }
}
