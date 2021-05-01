package com.github.iunius118.rxhandcart;

import com.github.iunius118.rxhandcart.capability.CapabilityHandcartHandler;
import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ItemHandlerCapabilityProvider;
import com.github.iunius118.rxhandcart.data.ModItemModelProvider;
import com.github.iunius118.rxhandcart.data.ModLanguageProviders;
import com.github.iunius118.rxhandcart.item.HandcartItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Mod(RxHandcart.MOD_ID)
public class RxHandcart {
    public static final String MOD_ID = "rxhandcart";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation HANDCART_KEY = new ResourceLocation(MOD_ID, "handcart");

    public RxHandcart() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Register capabilities
        CapabilityHandcartHandler.register();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void onAttachCapabilityEventEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();

        if(entity instanceof PlayerEntity) {
            event.addCapability(HANDCART_KEY, new ItemHandlerCapabilityProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;    // Return from End

        // Copy old capability's stacks to new capability
        PlayerEntity oldPlayer = event.getOriginal();
        PlayerEntity newPlayer = event.getPlayer();
        Optional<IHandcartHandler> oldHandlerOptional = oldPlayer.getCapability(CapabilityHandcartHandler.HANDCART_HANDLER_CAPABILITY).resolve();
        Optional<IHandcartHandler> newHandlerOptional = newPlayer.getCapability(CapabilityHandcartHandler.HANDCART_HANDLER_CAPABILITY).resolve();

        if (oldHandlerOptional.isPresent() && newHandlerOptional.isPresent()) {
            NonNullList<ItemStack> stacks = oldHandlerOptional.get().getStacks();
            newHandlerOptional.get().cloneStacksFrom(stacks);
        }
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            itemRegistryEvent.getRegistry().registerAll(
                    new HandcartItem(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName("handcart")
            );
        }

        @SubscribeEvent
        public static void gatherData(final GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();

            if (event.includeServer()) {

            }

            if (event.includeClient()) {
                ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
                generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
                ModLanguageProviders.addTo(generator);
            }
        }
    }
}
