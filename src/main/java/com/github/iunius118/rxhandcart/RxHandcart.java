package com.github.iunius118.rxhandcart;

import com.github.iunius118.rxhandcart.data.ModItemModelProvider;
import com.github.iunius118.rxhandcart.data.ModLanguageProviders;
import com.github.iunius118.rxhandcart.item.HandcartItem;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RxHandcart.MOD_ID)
public class RxHandcart {
    public static final String MOD_ID = "rxhandcart";

    private static final Logger LOGGER = LogManager.getLogger();

    public RxHandcart() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
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
