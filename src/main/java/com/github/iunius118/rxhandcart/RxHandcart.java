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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
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

        if(entity instanceof Player) {
            // Add Handcart capability to players
            event.addCapability(HANDCART_KEY, new HandcartHandlerCapability.Provider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player newPlayer = event.getPlayer();
        Player oldPlayer = event.getOriginal();

        if (event.isWasDeath()) {
            // Copy old capability data to new capability when player has respawned
            cloneHandcartHandler(oldPlayer, newPlayer);
        }
    }

    private void cloneHandcartHandler(Player oldPlayer, Player newPlayer){
        Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
        Optional<IHandcartHandler> oldHandlerOptional = oldPlayer.getCapability(capability).resolve();
        Optional<IHandcartHandler> newHandlerOptional = newPlayer.getCapability(capability).resolve();

        if (oldHandlerOptional.isPresent() && newHandlerOptional.isPresent()) {
            newHandlerOptional.get().cloneFrom(oldHandlerOptional.get());
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        if (!(player instanceof ServerPlayer)) return;

        ServerPlayer owner = (ServerPlayer) player;
        OptionalInt type = getHandcartType(owner);
        if (!type.isPresent()) return;

        // Send cart type of logged in player to their client
        sendChangeCartPacket(owner, type.getAsInt(), owner);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (!(player instanceof ServerPlayer)) return;

        ServerPlayer owner = (ServerPlayer) player;
        OptionalInt type = getHandcartType(owner);
        if (!type.isPresent()) return;

        // Send cart type of respawned player to their client
        sendChangeCartPacket(owner, type.getAsInt(), owner);
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        Player player = event.getPlayer();
        if (!(player instanceof ServerPlayer)) return;

        ServerPlayer receiver = (ServerPlayer) player;
        Entity owner = event.getTarget();
        if (!(owner instanceof Player)) return;

        OptionalInt type = getHandcartType(owner);
        if (!type.isPresent()) return;

        // Send cart type of owner to client of other player who appeared around owner
        sendChangeCartPacket(owner, type.getAsInt(), receiver);
    }

    public static OptionalInt getHandcartType(Entity owner) {
        Optional<IHandcartHandler> capability = owner.getCapability(ModCapabilities.HANDCART_HANDLER_CAPABILITY).resolve();
        if (!capability.isPresent()) return OptionalInt.empty();

        IHandcartHandler handcartHandler = capability.get();
        return OptionalInt.of(handcartHandler.getType());
    }

    public static void sendChangeCartPacket(Entity owner, int type, ServerPlayer receiver) {
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
                    new HandcartItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)).setRegistryName("handcart"),
                    new HandcartSettingItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 1).setRegistryName("handcart_setting")
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
