package com.github.iunius118.rxhandcart.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityHandcartHandler {
    @CapabilityInject(IHandcartHandler.class)
    public static Capability<IHandcartHandler> HANDCART_HANDLER_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IHandcartHandler.class, new Capability.IStorage<IHandcartHandler>() {
            @Override
            public INBT writeNBT(Capability<IHandcartHandler> capability, IHandcartHandler instance, Direction side) {
                ItemStackHandler itemHandler = instance.getItemHandler();
                ListNBT nbtTagList = new ListNBT();
                int size = itemHandler.getSlots();

                for (int i = 0; i < size; i++) {
                    ItemStack stack = itemHandler.getStackInSlot(i);

                    if (!stack.isEmpty()) {
                        CompoundNBT itemTag = new CompoundNBT();
                        itemTag.putInt("Slot", i);
                        stack.save(itemTag);
                        nbtTagList.add(itemTag);
                    }
                }

                return nbtTagList;
            }

            @Override
            public void readNBT(Capability<IHandcartHandler> capability, IHandcartHandler instance, Direction side, INBT base) {
                ItemStackHandler itemHandler = instance.getItemHandler();
                ListNBT tagList = (ListNBT) base;

                for (int i = 0; i < tagList.size(); i++) {
                    CompoundNBT itemTags = tagList.getCompound(i);
                    int j = itemTags.getInt("Slot");

                    if (j >= 0 && j < itemHandler.getSlots()) {
                        itemHandler.setStackInSlot(j, ItemStack.of(itemTags));
                    }
                }
            }
        }, HandcartItemStackHandler::new);
    }
}
