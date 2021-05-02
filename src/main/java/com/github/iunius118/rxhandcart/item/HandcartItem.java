package com.github.iunius118.rxhandcart.item;

import com.github.iunius118.rxhandcart.capability.CapabilityHandcartHandler;
import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.inventory.HandcartInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;

public class HandcartItem extends Item {
    public static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.rxhandcart.handcart");

    public HandcartItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return ActionResult.success(stack);
        } else {
            openCartChest(player, stack);   // Open Handcart inventory's GUI
            return ActionResult.consume(stack);
        }
    }

    private void openCartChest(PlayerEntity player, ItemStack stack) {
        IInventory inventory = getCartInventory(player);

        if (inventory != null) {
            player.openMenu(new SimpleNamedContainerProvider((id, pi, p) -> ChestContainer.threeRows(id, pi, inventory), getContainerTitle(stack)));
            player.awardStat(Stats.CUSTOM.get(Stats.OPEN_CHEST));
        }
    }

    @Nullable
    private IInventory getCartInventory(PlayerEntity player) {
        Optional<IHandcartHandler> handlerOptional = player.getCapability(CapabilityHandcartHandler.HANDCART_HANDLER_CAPABILITY).resolve();
        // Wrap capability in inventory
        return handlerOptional.map(iHandcartHandler -> new HandcartInventory(iHandcartHandler.getItemHandler())).orElse(null);
    }

    private ITextComponent getContainerTitle(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            return stack.getDisplayName();
        }

        return CONTAINER_TITLE;
    }
}
