package com.github.iunius118.rxhandcart.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public class HandcartHandlerStorage implements Capability.IStorage<IHandcartHandler> {
    public final static String KET_INV = "Inv";
    public final static String KET_INV_SLOT = "Slot";

    @Override
    public INBT writeNBT(Capability<IHandcartHandler> capability, IHandcartHandler instance, Direction side) {
        ItemStackHandler itemHandler = instance.getItemHandler();
        ListNBT nbtTagList = new ListNBT();
        int size = itemHandler.getSlots();

        for (int i = 0; i < size; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);

            if (!stack.isEmpty()) {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt(KET_INV_SLOT, i);
                stack.save(itemTag);
                nbtTagList.add(itemTag);
            }
        }

        CompoundNBT tag = new CompoundNBT();
        tag.put(KET_INV, nbtTagList);

        return tag;
    }

    @Override
    public void readNBT(Capability<IHandcartHandler> capability, IHandcartHandler instance, Direction side, INBT base) {
        if (!(base instanceof CompoundNBT)) return;

        CompoundNBT tag = (CompoundNBT) base;

        ListNBT tagList = (ListNBT) tag.get(KET_INV);

        if (tagList != null) {
            ItemStackHandler itemHandler = instance.getItemHandler();

            for (int i = 0; i < tagList.size(); i++) {
                CompoundNBT itemTags = tagList.getCompound(i);
                int j = itemTags.getInt(KET_INV_SLOT);

                if (j >= 0 && j < itemHandler.getSlots()) {
                    itemHandler.setStackInSlot(j, ItemStack.of(itemTags));
                }
            }
        }
    }
}
