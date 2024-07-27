package brmv.longer_days.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Surrogate;


@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {


    private static final long LENGTH_MODIFIER = 3;

    @Shadow
    @Final
    private boolean shouldTickTime;

    @Shadow
    public abstract void setTimeOfDay(long timeOfDay);

    @Inject(at = @At("RETURN"), method = "tickTime")
    private void tickTime(CallbackInfo info) {

        // if statements need to be copied from base tickTime to make sure they work with gamerules
        if (this.shouldTickTime) {
            var properties = ((WorldMixin) this)
                    .getProperties();
            var doDaylightCycle = properties
                    .getGameRules()
                    .getBoolean(GameRules.DO_DAYLIGHT_CYCLE);
            if (doDaylightCycle) {
                long newTimeOfDay = properties.getTimeOfDay();
                newTimeOfDay -= 1; // undo effect of previous day setting
                if (properties.getTime() % LENGTH_MODIFIER == 0) {
                    newTimeOfDay += 1;
                }
                this.setTimeOfDay(newTimeOfDay);
            }
        }
    }
}


@Mixin(World.class)
interface WorldMixin {

    @Accessor
    MutableWorldProperties getProperties();
}