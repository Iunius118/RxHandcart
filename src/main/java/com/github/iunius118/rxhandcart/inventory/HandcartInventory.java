package com.github.iunius118.rxhandcart.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class HandcartInventory implements IInventory {
    public final static int SIZE = 27;
    private final ItemStackHandler itemStackHandler;

    public HandcartInventory(ItemStackHandler handler) {
        itemStackHandler = handler;
    }

    @Override
    public int getContainerSize() {
        return itemStackHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            ItemStack stack = itemStackHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemStackHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return itemStackHandler.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = getItem(slot);

        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            setItem(slot, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        itemStackHandler.insertItem(slot, stack, false);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public void clearContent() {
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
