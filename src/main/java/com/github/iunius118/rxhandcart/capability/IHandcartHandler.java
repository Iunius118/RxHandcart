package com.github.iunius118.rxhandcart.capability;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.items.ItemStackHandler;
import org.joml.Vector2d;

import javax.annotation.Nonnull;

@AutoRegisterCapability
public interface IHandcartHandler {
    /**
     * Change appearance of handcart
     * @param handcartType Handcart type to change
     */
    void setType(int handcartType);

    /**
     * Get handcart type
     * @return Handcart type
     */
    int getType();

    /**
     * Get handcart's {@link ItemStackHandler}
     * @return {@link ItemStackHandler} of handcart
     */
    @Nonnull
    ItemStackHandler getItemHandler();

    /**
     * Copy handcart data form other handcart
     * @param original Handcart from which data is copied
     */
    void cloneFrom(@Nonnull IHandcartHandler original);

    /**
     * Set handcart position. This method is only valid on client side
     * @param position New position of handcart
     */
    void setPosition(Vector2d position);

    /**
     * Get handcart position. This method is only valid on client side
     * @param partialTick Partial tick
     * @return Handcart position
     */
    Vector2d getPosition(float partialTick);

    /**
     * Set handcart direction. This method is only valid on client side
     * @param direction New direction of handcart
     */
    void setDirection(float direction);

    /**
     * Get handcart direction. This method is only valid on client side
     * @param partialTick Partial tick
     * @return Handcart direction
     */
    float getDirection(float partialTick);
}
