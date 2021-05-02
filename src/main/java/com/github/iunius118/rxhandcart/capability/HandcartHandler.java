package com.github.iunius118.rxhandcart.capability;

import com.github.iunius118.rxhandcart.inventory.HandcartInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class HandcartHandler implements IHandcartHandler {
    private final ItemStackHandler itemStackHandler;

    public HandcartHandler() {
        itemStackHandler = new ItemStackHandler(HandcartInventory.SIZE);
    }

    @Override
    @Nonnull
    public ItemStackHandler getItemHandler() {
        return itemStackHandler;
    }

    @Override
    public void cloneStacksFrom(@Nonnull IHandcartHandler original) {
        ItemStackHandler originalStackHandler = original.getItemHandler();
        int maxSize = Math.min(itemStackHandler.getSlots(), originalStackHandler.getSlots());

        for(int i = 0; i < maxSize; i++) {
            ItemStack originalStack = originalStackHandler.getStackInSlot(i);
            itemStackHandler.setStackInSlot(i, originalStack);
        }
    }
}
