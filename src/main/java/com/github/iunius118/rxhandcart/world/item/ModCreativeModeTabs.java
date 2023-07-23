package com.github.iunius118.rxhandcart.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab MAIN = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.rxhandcart"))
            .icon(() -> new ItemStack(ModItems.HANDCART))
            .displayItems((params, output) -> {
                output.accept(ModItems.HANDCART);
                output.accept(ModItems.HANDCART_SETTING);
            })
            .build();
}
