package com.github.iunius118.rxhandcart.capability;

import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public interface IHandcartHandler {
    @Nonnull
    ItemStackHandler getItemHandler();

    void cloneStacksFrom(@Nonnull IHandcartHandler original);
}
