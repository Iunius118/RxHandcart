package com.github.iunius118.rxhandcart.world.item;

import net.minecraft.world.item.Item;

public class ModItems {
    private static final Item.Properties itemProperties = new Item.Properties().tab(ModItemGroups.MAIN);

    public static final Item HANDCART = new HandcartItem(itemProperties);
    public static final Item HANDCART_SETTING = new HandcartSettingItem(itemProperties, 1);
}
