package com.github.iunius118.rxhandcart.item;

import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import com.github.iunius118.rxhandcart.inventory.HandcartInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;

public class HandcartItem extends Item {
    public static final Component CONTAINER_TITLE = Component.translatable("container.rxhandcart.handcart");

    public HandcartItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return InteractionResultHolder.success(stack);
        } else {
            // Open Handcart inventory's GUI
            openCartChest(player, stack);
            return InteractionResultHolder.consume(stack);
        }
    }

    private void openCartChest(Player player, ItemStack stack) {
        Container inventory = getCartInventory(player);

        if (inventory != null) {
            Component title = getContainerTitle(stack);
            player.openMenu(new SimpleMenuProvider((id, pi, p) -> ChestMenu.threeRows(id, pi, inventory), title));
            player.awardStat(Stats.CUSTOM.get(Stats.OPEN_CHEST));
        }
    }

    @Nullable
    private Container getCartInventory(Player player) {
        Optional<IHandcartHandler> handlerOptional = player.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        // Wrap capability's item handler in inventory object
        // if handcartHandler != null then return inventory else return null
        return handlerOptional.map(handcartHandler -> new HandcartInventory(handcartHandler.getItemHandler())).orElse(null);
    }

    private Component getContainerTitle(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            return stack.getHoverName();
        } else {
            return CONTAINER_TITLE;
        }
    }
}
