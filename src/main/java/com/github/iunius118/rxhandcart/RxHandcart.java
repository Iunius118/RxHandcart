package com.github.iunius118.rxhandcart;

import com.github.iunius118.rxhandcart.capability.HandcartHandlerCapability;
import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import com.github.iunius118.rxhandcart.client.ClientEventHandler;
import com.github.iunius118.rxhandcart.data.ModItemModelProvider;
import com.github.iunius118.rxhandcart.data.ModLanguageProviders;
import com.github.iunius118.rxhandcart.data.ModRecipeProvider;
import com.github.iunius118.rxhandcart.item.HandcartItem;
import com.github.iunius118.rxhandcart.item.HandcartSettingItem;
import com.github.iunius118.rxhandcart.network.ChangeCartMessage;
import com.github.iunius118.rxhandcart.network.NetworkHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.OptionalInt;

@Mod(RxHandcart.MOD_ID)
public class RxHandcart {
    public static final String MOD_ID = "rxhandcart";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation HANDCART_KEY = new ResourceLocation(MOD_ID, "handcart");

    // Init network channels
    public static final NetworkHandler NETWORK_HANDLER = new NetworkHandler();

    public RxHandcart() {
        // Register lifecycle event listeners
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        // Register event handlers
        MinecraftForge.EVENT_BUS.register(this);

        // Register client-side event handler
        if (FMLLoader.getDist().isClient()) {
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Register capabilities
        HandcartHandlerCapability.register();
    }

    @SubscribeEvent
    public void onAttachCapabilityEventEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();

        if(entity instanceof PlayerEntity) {
            // Add Handcart capability to players
            event.addCapability(HANDCART_KEY, new HandcartHandlerCapability.Provider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        PlayerEntity newPlayer = event.getPlayer();
        PlayerEntity oldPlayer = event.getOriginal();

        if (event.isWasDeath()) {
            // Copy old capability data to new capability when player has respawned
            cloneHandcartHandler(oldPlayer, newPlayer);
        }
    }

    private void cloneHandcartHandler(PlayerEntity oldPlayer, PlayerEntity newPlayer){
        Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
        Optional<IHandcartHandler> oldHandlerOptional = oldPlayer.getCapability(capability).resolve();
        Optional<IHandcartHandler> newHandlerOptional = newPlayer.getCapability(capability).resolve();

        if (oldHandlerOptional.isPresent() && newHandlerOptional.isPresent()) {
            newHandlerOptional.get().cloneFrom(oldHandlerOptional.get());
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!(player instanceof ServerPlayerEntity)) return;

        ServerPlayerEntity owner = (ServerPlayerEntity) player;
        OptionalInt type = getHandcartType(owner);
        if (!type.isPresent()) return;

        sendChangeCartPacket(owner, type.getAsInt(), owner);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!(player instanceof ServerPlayerEntity)) return;

        ServerPlayerEntity owner = (ServerPlayerEntity) player;
        OptionalInt type = getHandcartType(owner);
        if (!type.isPresent()) return;

        sendChangeCartPacket(owner, type.getAsInt(), owner);
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        PlayerEntity player = event.getPlayer();
        if (!(player instanceof ServerPlayerEntity)) return;

        ServerPlayerEntity receiver = (ServerPlayerEntity) player;
        Entity owner = event.getTarget();
        if (!(owner instanceof PlayerEntity)) return;

        OptionalInt type = getHandcartType(owner);
        if (!type.isPresent()) return;

        sendChangeCartPacket(owner, type.getAsInt(), receiver);
    }

    public static OptionalInt getHandcartType(Entity owner) {
        Optional<IHandcartHandler> capability = owner.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        if (!capability.isPresent()) return OptionalInt.empty();

        IHandcartHandler handcartHandler = capability.get();
        return OptionalInt.of(handcartHandler.getType());
    }

    public static void sendChangeCartPacket(Entity owner, int type, ServerPlayerEntity receiver) {
        SimpleChannel changeCartChannel = NETWORK_HANDLER.getChangeCartChannel();
        PacketDistributor.PacketTarget target = PacketDistributor.PLAYER.with(() -> receiver);
        ChangeCartMessage message = new ChangeCartMessage(owner, type);
        changeCartChannel.send(target, message);
    }

    public static void broadcastChangeCartPacket(Entity owner, int type) {
        SimpleChannel changeCartChannel = NETWORK_HANDLER.getChangeCartChannel();
        PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> owner);
        ChangeCartMessage message = new ChangeCartMessage(owner, type);
        changeCartChannel.send(target, message);
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
                generator.addProvider(new ModRecipeProvider(generator));
            }

            if (event.includeClient()) {
                ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
                generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
                ModLanguageProviders.addTo(generator);
            }
        }
    }
}
