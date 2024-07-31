package brmv.longer_days.mixin;

import brmv.longer_days.LongerDays;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Shadow
    public abstract void setTimeOfDay(long timeOfDay);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/MutableWorldProperties;getTimeOfDay()J"), method = "tickTime")
    private void tickTime(CallbackInfo info) {
        var newTimeOfDay = LongerDays.incrementTimeOfDay((WorldMixin) this);
        this.setTimeOfDay(newTimeOfDay);
    }

}


