package com.github.iunius118.rxhandcart.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public interface IHandcartHandler {
    ItemStackHandler getItemHandler();

    NonNullList<ItemStack> getStacks();

    void cloneStacksFrom(NonNullList<ItemStack> stacks);
}
