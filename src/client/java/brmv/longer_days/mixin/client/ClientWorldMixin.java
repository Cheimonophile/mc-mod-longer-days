package brmv.longer_days.mixin.client;


import brmv.longer_days.LongerDays;
import brmv.longer_days.mixin.WorldMixin;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    @Shadow
    public abstract void setTimeOfDay(long timeOfDay);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/MutableWorldProperties;getTimeOfDay()J"), method = "tickTime")
    private void init(CallbackInfo info) {
        var newTimeOfDay = LongerDays.incrementTimeOfDay((WorldMixin) this);
        this.setTimeOfDay(newTimeOfDay);
    }
}