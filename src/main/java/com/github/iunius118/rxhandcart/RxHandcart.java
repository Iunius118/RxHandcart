package com.github.iunius118.rxhandcart;

import com.github.iunius118.rxhandcart.capability.HandcartHandlerCapability;
import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import com.github.iunius118.rxhandcart.client.ClientEventHandler;
import com.github.iunius118.rxhandcart.common.PlayerEventHandler;
import com.github.iunius118.rxhandcart.data.ModItemModelProvider;
import com.github.iunius118.rxhandcart.data.ModLanguageProvider;
import com.github.iunius118.rxhandcart.data.ModRecipeProvider;
import com.github.iunius118.rxhandcart.network.NetworkHandler;
import com.github.iunius118.rxhandcart.world.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.OptionalInt;

@Mod(RxHandcart.MOD_ID)
public class RxHandcart {
    public static final String MOD_ID = "rxhandcart";
    public static final String MOD_NAME ="RxHandcart";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation HANDCART_KEY = new ResourceLocation(MOD_ID, "handcart");

    // Init network channels
    public static final NetworkHandler NETWORK_HANDLER = new NetworkHandler();

    public RxHandcart() {
        // Register lifecycle event listeners
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register event handlers
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::onAttachCapabilityEventEntity);
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());

        // Register mod event handlers
        modEventBus.addListener(this::registerItems);
        modEventBus.addListener(this::gatherData);

        // Register client-side event handler
        if (FMLLoader.getDist().isClient()) {
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        }
    }

    public static OptionalInt getHandcartType(Entity owner) {
        Optional<IHandcartHandler> capability = owner.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        if (capability.isEmpty())
            return OptionalInt.empty();

        IHandcartHandler handcartHandler = capability.get();
        return OptionalInt.of(handcartHandler.getType());
    }

    public void onAttachCapabilityEventEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();

        if(entity instanceof Player) {
            // Add Handcart capability to players
            event.addCapability(HANDCART_KEY, new HandcartHandlerCapability.Provider());
        }
    }

    public void registerItems(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registry.ITEM_REGISTRY))
            return;

        event.register(Registry.ITEM_REGISTRY, new ResourceLocation(RxHandcart.MOD_ID, "handcart"), () -> ModItems.HANDCART);
        event.register(Registry.ITEM_REGISTRY, new ResourceLocation(RxHandcart.MOD_ID, "handcart_setting"), () -> ModItems.HANDCART_SETTING);
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        HandcartHandlerCapability.register(event);
    }

    public void gatherData(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();

        dataGenerator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));

        boolean includeClient = event.includeClient();
        dataGenerator.addProvider(includeClient, new ModItemModelProvider(packOutput, existingFileHelper));
        ModLanguageProvider.addProviders(includeClient, dataGenerator, packOutput);
    }
}
