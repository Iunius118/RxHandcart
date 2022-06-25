package com.github.iunius118.rxhandcart.world.item;

import com.github.iunius118.rxhandcart.RxHandcart;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab MAIN = new CreativeModeTab(RxHandcart.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.HANDCART);
        }
    };
}
