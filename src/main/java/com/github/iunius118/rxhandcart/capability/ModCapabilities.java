package com.github.iunius118.rxhandcart.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapabilities {
    @CapabilityInject(IHandcartHandler.class)
    public static Capability<IHandcartHandler> HANDCART_HANDLER_CAPABILITY = null;
}
