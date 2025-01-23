package com.cerium.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ClientTickMixin {
    private int tickCounter = 0;
    private static final int CALCULATION_FREQUENCY = 5; // Perform calculation every 5 ticks

    @Inject(method = "tick", at = @At("HEAD"))
    private void onClientTick(CallbackInfo ci) {
        tickCounter++;
        if (tickCounter >= CALCULATION_FREQUENCY) {
            tickCounter = 0;
        }
    }
}