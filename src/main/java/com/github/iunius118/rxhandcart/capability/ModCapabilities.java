package com.github.iunius118.rxhandcart.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<IHandcartHandler> HANDCART_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
}
