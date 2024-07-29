package brmv.longer_days.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    /**
     * <p>
     * How frequently to update the time
     * </p>
     *
     * <p>
     * e.g. 3 means every third tick will update the time
     * </p>
     */
    @Unique
    private static final long LENGTH_MOD = 3;

    @Shadow
    public abstract void setTimeOfDay(long timeOfDay);

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/MutableWorldProperties;getTimeOfDay()J"), method = "tickTime")
    private void tickTime(CallbackInfo info) {
        var properties = ((WorldMixin) this).getProperties();
        long newTimeOfDay = properties.getTimeOfDay();
        newTimeOfDay -= 1; // undo effect of day setting that's about to happen
        if (properties.getTime() % LENGTH_MOD == 0) {
            newTimeOfDay += 1;
        }
        this.setTimeOfDay(newTimeOfDay);
    }

//    @Inject(at = @At("RETURN"), method = "tickTime")
//    private void logTickTime(CallbackInfo info) {
//        LongerDays.LOGGER.info("tickTime");
//    }
//
//    @Inject(at = @At("RETURN"), method = "tick")
//    private void logTick(CallbackInfo info) {
//        LongerDays.LOGGER.info("tick");
//    }

}


@Mixin(World.class)
interface WorldMixin {

    @Accessor
    MutableWorldProperties getProperties();
}