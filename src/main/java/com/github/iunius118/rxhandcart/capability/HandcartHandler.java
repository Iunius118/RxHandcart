package com.github.iunius118.rxhandcart.capability;

import com.github.iunius118.rxhandcart.inventory.HandcartInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.joml.Vector2d;

import javax.annotation.Nonnull;

public class HandcartHandler implements IHandcartHandler {
    public static final int INVISIBLE_TYPE = 0;
    private int type = INVISIBLE_TYPE;
    private final ItemStackHandler itemStackHandler;

    public HandcartHandler() {
        itemStackHandler = new ItemStackHandler(HandcartInventory.SIZE);
    }

    @Override
    public void setType(int handcartType) {
        type = handcartType;
    }

    @Override
    public int getType() {
        return type;
    }

    @Nonnull
    @Override
    public ItemStackHandler getItemHandler() {
        return itemStackHandler;
    }

    @Override
    public void cloneFrom(@Nonnull IHandcartHandler original) {
        setType(original.getType());

        ItemStackHandler originalStackHandler = original.getItemHandler();
        int maxSize = Math.min(itemStackHandler.getSlots(), originalStackHandler.getSlots());

        for(int i = 0; i < maxSize; i++) {
            ItemStack originalStack = originalStackHandler.getStackInSlot(i);
            itemStackHandler.setStackInSlot(i, originalStack);
        }
    }

    private Vector2d pos;
    private float dir = 0;

    @Override
    public void setPosition(Vector2d position) {
        pos = position;
    }

    @Override
    public Vector2d getPosition(float partialTick) {
        return pos;
    }

    @Override
    public void setDirection(float direction) {
        dir = direction;
    }

    @Override
    public float getDirection(float partialTick) {
        return dir;
    }
}
