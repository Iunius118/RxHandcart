package com.github.iunius118.rxhandcart.capability;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

@AutoRegisterCapability
public interface IHandcartHandler {
    void setType(int handcartType);

    int getType();

    @Nonnull
    ItemStackHandler getItemHandler();

    void cloneFrom(@Nonnull IHandcartHandler original);
}
