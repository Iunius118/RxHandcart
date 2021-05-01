package com.github.iunius118.rxhandcart.capability;

import com.github.iunius118.rxhandcart.inventory.HandcartInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class HandcartItemStackHandler extends ItemStackHandler implements IHandcartHandler {
    public HandcartItemStackHandler() {
        super(HandcartInventory.SIZE);
    }

    @Override
    public ItemStackHandler getItemHandler() {
        return this;
    }

    @Override
    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    @Override
    public void cloneStacksFrom(NonNullList<ItemStack> stacks) {
        int maxSize = Math.min(this.getSlots(), stacks.size());

        for(int i = 0; i < maxSize; i++) {
            this.setStackInSlot(i, stacks.get(i));
        }
    }
}
