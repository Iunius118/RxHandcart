package com.github.iunius118.rxhandcart.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HandcartHandlerCapability {
    public static void register() {
        CapabilityManager.INSTANCE.register(IHandcartHandler.class, new HandcartHandlerStorage(), HandcartHandler::new);
    }

    public static class Provider implements ICapabilitySerializable<INBT> {
        protected LazyOptional<IHandcartHandler> inst = LazyOptional.of(HandcartHandler::new);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return ModCapabilities.HANDCART_HANDLER_CAPABILITY.orEmpty(cap, inst);
        }

        @Override
        public INBT serializeNBT() {
            Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
            Capability.IStorage<IHandcartHandler> storage = capability.getStorage();
            IHandcartHandler handcartHandler = inst.orElseGet(HandcartHandler::new);
            return storage.writeNBT(capability, handcartHandler, null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
            Capability.IStorage<IHandcartHandler> storage = capability.getStorage();
            IHandcartHandler handcartHandler = inst.orElseGet(HandcartHandler::new);
            storage.readNBT(capability, handcartHandler, null, nbt);
        }
    }
}
