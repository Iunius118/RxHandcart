package com.github.iunius118.rxhandcart.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {
    private static final Item.Properties itemProperties = new Item.Properties().tab(CreativeModeTab.TAB_MISC);

    public static final Item HANDCART = new HandcartItem(itemProperties);
    public static final Item HANDCART_SETTING = new HandcartSettingItem(itemProperties, 1);
}
