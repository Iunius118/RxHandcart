package com.github.iunius118.rxhandcart.world.item;

import com.github.iunius118.rxhandcart.RxHandcart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModItemGroups {
    private static CreativeModeTab MAIN;

    @SubscribeEvent
    public static void onCreativeModeTabRegister(CreativeModeTabEvent.Register event) {
        ModItemGroups.MAIN = event.registerCreativeModeTab(new ResourceLocation(RxHandcart.MOD_ID, "main"),
                builder -> builder.icon(() -> new ItemStack(ModItems.HANDCART))
                        .title(Component.translatable("itemGroup.rxhandcart"))
                        .displayItems((features, output, hasPermissions) -> {
                            output.accept(ModItems.HANDCART);
                            output.accept(ModItems.HANDCART_SETTING);
                        })
        );
    }

    public static CreativeModeTab getMain() {
        return MAIN;
    }
}
