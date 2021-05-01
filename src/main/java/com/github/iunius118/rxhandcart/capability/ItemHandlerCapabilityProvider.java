package com.github.iunius118.rxhandcart.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemHandlerCapabilityProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IHandcartHandler.class)
    public static Capability<IHandcartHandler> HANDCART_HANDLER_CAPABILITY = null;
    protected LazyOptional<IHandcartHandler> inst = LazyOptional.of(HandcartItemStackHandler::new);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return HANDCART_HANDLER_CAPABILITY.orEmpty(cap, inst);
    }

    @Override
    public INBT serializeNBT() {
        IHandcartHandler handcartHandler = inst.orElseGet(HandcartItemStackHandler::new);
        Capability.IStorage<IHandcartHandler> storage = HANDCART_HANDLER_CAPABILITY.getStorage();
        return storage.writeNBT(HANDCART_HANDLER_CAPABILITY, handcartHandler, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        IHandcartHandler handcartHandler = inst.orElseGet(HandcartItemStackHandler::new);
        Capability.IStorage<IHandcartHandler> storage = HANDCART_HANDLER_CAPABILITY.getStorage();
        storage.readNBT(HANDCART_HANDLER_CAPABILITY, handcartHandler, null, nbt);
    }
}
