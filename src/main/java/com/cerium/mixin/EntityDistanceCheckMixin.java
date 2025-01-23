package com.cerium.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityDistanceCheckMixin {
    private Vec3d lastPosition = null;
    private double lastDistanceSquared = -1;
    private int distanceCheckCounter = 0;
    private static final int DISTANCE_CHECK_FREQUENCY = 5; // Check every 5 ticks

    @Inject(method = "squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D", at = @At("HEAD"), cancellable = true)
    private void onSquaredDistanceTo(Vec3d vec, CallbackInfoReturnable<Double> cir) {
        if (lastPosition != null && distanceCheckCounter < DISTANCE_CHECK_FREQUENCY && ((Entity) (Object) this).getPos().squaredDistanceTo(lastPosition) < 0.01) {
            cir.setReturnValue(lastDistanceSquared);
        } else {
            lastPosition = ((Entity) (Object) this).getPos();
            lastDistanceSquared = ((Entity) (Object) this).getPos().squaredDistanceTo(vec);
            distanceCheckCounter = 0;
        }
        distanceCheckCounter++;
    }
}