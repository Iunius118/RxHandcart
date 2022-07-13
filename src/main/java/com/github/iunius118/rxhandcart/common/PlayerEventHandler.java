package com.github.iunius118.rxhandcart.common;

import com.github.iunius118.rxhandcart.RxHandcart;
import com.github.iunius118.rxhandcart.capability.IHandcartHandler;
import com.github.iunius118.rxhandcart.capability.ModCapabilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;
import java.util.OptionalInt;

public class PlayerEventHandler {
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player newPlayer = event.getEntity();
        Player oldPlayer = event.getOriginal();

        if (event.isWasDeath()) {
            // Copy old capability data to new capability when player has respawned
            cloneHandcartHandler(oldPlayer, newPlayer);
        }
    }

    private void cloneHandcartHandler(Player oldPlayer, Player newPlayer){
        Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
        Optional<IHandcartHandler> oldHandlerOptional = getHandcartHandlerFromRemovedPlayer(oldPlayer);
        Optional<IHandcartHandler> newHandlerOptional = newPlayer.getCapability(capability).resolve();

        if (oldHandlerOptional.isPresent() && newHandlerOptional.isPresent()) {
            newHandlerOptional.get().cloneFrom(oldHandlerOptional.get());
        }
    }

    private Optional<IHandcartHandler> getHandcartHandlerFromRemovedPlayer(Player removedPlayer) {
        Capability<IHandcartHandler> capability = ModCapabilities.HANDCART_HANDLER_CAPABILITY;
        removedPlayer.reviveCaps();
        Optional<IHandcartHandler> handlerOptional = removedPlayer.getCapability(capability).resolve();
        removedPlayer.invalidateCaps();
        return handlerOptional;
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer owner))
            return;

        OptionalInt type = RxHandcart.getHandcartType(owner);
        if (type.isEmpty())
            return;

        // Send cart type of logged in player to their client
        RxHandcart.NETWORK_HANDLER.sendChangeCartPacket(owner, type.getAsInt(), owner);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer owner))
            return;

        OptionalInt type = RxHandcart.getHandcartType(owner);
        if (type.isEmpty())
            return;

        // Send cart type of respawned player to their client
        RxHandcart.NETWORK_HANDLER.sendChangeCartPacket(owner, type.getAsInt(), owner);
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer receiver))
            return;

        Entity owner = event.getTarget();
        if (!(owner instanceof Player))
            return;

        OptionalInt type = RxHandcart.getHandcartType(owner);
        if (type.isEmpty())
            return;

        // Send cart type of owner to client of other player who appeared around owner
        RxHandcart.NETWORK_HANDLER.sendChangeCartPacket(owner, type.getAsInt(), receiver);
    }
}
