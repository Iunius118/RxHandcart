package com.github.iunius118.rxhandcart.item;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.capability.HandcartHandler;
import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Optional;

public class HandcartSettingItem extends Item {
    private final static int COOL_DOWN = 40;
    private final int type;

    public HandcartSettingItem(Item.Properties properties, int handcartType) {
        super(properties);

        type = handcartType;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return ActionResult.success(stack);
        } else {
            switchHandcartType(player);
            return ActionResult.consume(stack);
        }
    }

    private void switchHandcartType(PlayerEntity player) {
        Optional<IHandcartHandler> capability = player.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        if (!capability.isPresent()) return;

        IHandcartHandler handcartHandler = capability.get();
        int oldType = handcartHandler.getType();
        int newType = getType();

        if (newType == oldType) {
            // Make handcart invisible
            newType = HandcartHandler.INVISIBLE_TYPE;
        }

        // Update capability in server
        handcartHandler.setType(newType);
        // Update visibility of handcart in each client
        RxHandcart.broadcastChangeCartPacket(player, newType);
        // Ass cool-down time
        CooldownTracker cooldownTracker = player.getCooldowns();
        cooldownTracker.addCooldown(this, getCoolDownTime());
    }

    public int getType() {
        return type;
    }

    public int getCoolDownTime() {
        return COOL_DOWN;
    }
}
