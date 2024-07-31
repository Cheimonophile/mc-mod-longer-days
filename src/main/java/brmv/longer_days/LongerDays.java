package brmv.longer_days;

import brmv.longer_days.mixin.WorldMixin;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongerDays implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("longer-days");

	/**
	 * <p>
	 * How frequently to update the time
	 * </p>
	 *
	 * <p>
	 * e.g. 3 means every third tick will update the time
	 * </p>
	 */
	public static final long LENGTH_MOD = 3;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}


	/**
	 * Calculates the new time of day given the world properties
	 *
	 * @param worldMixin The world mixin
	 * @return the new time of day
	 */
	public static long incrementTimeOfDay(WorldMixin worldMixin) {
		var properties = worldMixin.getProperties();
		long newTimeOfDay = properties.getTimeOfDay();
		newTimeOfDay -= 1; // undo effect of day setting that's about to happen
		if (properties.getTime() % LongerDays.LENGTH_MOD == 0) {
			newTimeOfDay += 1;
		}
		return newTimeOfDay;
	}







}