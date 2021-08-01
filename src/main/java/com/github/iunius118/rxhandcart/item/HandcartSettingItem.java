package com.github.iunius118.rxhandcart.item;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.capability.HandcartHandler;
import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class HandcartSettingItem extends Item {
    private final static int COOL_DOWN = 40;
    private final int type;

    public HandcartSettingItem(Item.Properties properties, int handcartType) {
        super(properties);

        type = handcartType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return InteractionResultHolder.success(stack);
        } else {
            switchHandcartType(player);
            return InteractionResultHolder.consume(stack);
        }
    }

    private void switchHandcartType(Player player) {
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
        // Add cool-down time
        ItemCooldowns cooldownTracker = player.getCooldowns();
        cooldownTracker.addCooldown(this, getCoolDownTime());
    }

    public int getType() {
        return type;
    }

    public int getCoolDownTime() {
        return COOL_DOWN;
    }
}
