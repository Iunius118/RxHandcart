package com.github.iunius118.rxhandcart.item;

import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import com.github.iunius118.rxhandcart.client.HandcartManager;
import com.github.iunius118.rxhandcart.inventory.HandcartInventory;
import net.minecraft.entity.Entity;
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
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (world.isClientSide && stack.getItem() instanceof HandcartItem) {
            updateHandcart(entity); // Update handcarts on client-side world for rendering
        }
    }

    private void updateHandcart(Entity entity) {
        HandcartManager.addHandcart(entity);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return ActionResult.success(stack);
        } else {
            // Open Handcart inventory's GUI
            openCartChest(player, stack);
            return ActionResult.consume(stack);
        }
    }

    private void openCartChest(PlayerEntity player, ItemStack stack) {
        IInventory inventory = getCartInventory(player);

        if (inventory != null) {
            ITextComponent title = getContainerTitle(stack);
            player.openMenu(new SimpleNamedContainerProvider((id, pi, p) -> ChestContainer.threeRows(id, pi, inventory), title));
            player.awardStat(Stats.CUSTOM.get(Stats.OPEN_CHEST));
        }
    }

    @Nullable
    private IInventory getCartInventory(PlayerEntity player) {
        Optional<IHandcartHandler> handlerOptional = player.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        // Wrap capability's item handler in inventory object
        // if handcartHandler != null then return inventory else return null
        return handlerOptional.map(handcartHandler -> new HandcartInventory(handcartHandler.getItemHandler())).orElse(null);
    }

    private ITextComponent getContainerTitle(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            return stack.getHoverName();
        } else {
            return CONTAINER_TITLE;
        }
    }
}
