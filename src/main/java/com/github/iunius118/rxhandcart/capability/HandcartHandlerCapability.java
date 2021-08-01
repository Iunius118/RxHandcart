package com.github.iunius118.rxhandcart.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HandcartHandlerCapability {
    public static void register() {
        CapabilityManager.INSTANCE.register(IHandcartHandler.class);
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        protected LazyOptional<IHandcartHandler> inst = LazyOptional.of(HandcartHandler::new);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return ModCapabilities.HANDCART_HANDLER_CAPABILITY.orEmpty(cap, inst);
        }

        @Override
        public Tag serializeNBT() {
            Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
            IHandcartHandler handcartHandler = inst.orElseGet(HandcartHandler::new);
            return writeNBT(capability, handcartHandler, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
            IHandcartHandler handcartHandler = inst.orElseGet(HandcartHandler::new);
            readNBT(capability, handcartHandler, null, nbt);
        }

        public final static String KET_TYPE = "Type";
        public final static String KET_INV = "Inv";
        public final static String KET_INV_SLOT = "Slot";

        public Tag writeNBT(Capability<IHandcartHandler> capability, IHandcartHandler instance, Direction side) {
            CompoundTag tag = new CompoundTag();

            // Save type to NBT
            tag.putInt(KET_TYPE, instance.getType());

            // Save item stacks to NBT
            ItemStackHandler itemHandler = instance.getItemHandler();
            ListTag nbtTagList = new ListTag();
            int size = itemHandler.getSlots();

            for (int i = 0; i < size; i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);

                if (!stack.isEmpty()) {
                    CompoundTag itemTag = new CompoundTag();
                    itemTag.putInt(KET_INV_SLOT, i);
                    stack.save(itemTag);
                    nbtTagList.add(itemTag);
                }
            }

            tag.put(KET_INV, nbtTagList);

            return tag;
        }

        public void readNBT(Capability<IHandcartHandler> capability, IHandcartHandler instance, Direction side, Tag base) {
            if (!(base instanceof CompoundTag)) return;

            CompoundTag tag = (CompoundTag) base;

            // Load type from NBT
            if (tag.contains(KET_TYPE, Constants.NBT.TAG_INT)) {
                instance.setType(tag.getInt(KET_TYPE));
            } else {
                instance.setType(HandcartHandler.INVISIBLE_TYPE);
            }

            // Load item stacks from NBT
            ListTag tagList = (ListTag) tag.get(KET_INV);

            if (tagList != null) {
                ItemStackHandler itemHandler = instance.getItemHandler();

                for (int i = 0; i < tagList.size(); i++) {
                    CompoundTag itemTags = tagList.getCompound(i);
                    int j = itemTags.getInt(KET_INV_SLOT);

                    if (j >= 0 && j < itemHandler.getSlots()) {
                        itemHandler.setStackInSlot(j, ItemStack.of(itemTags));
                    }
                }
            }
        }
    }
}
