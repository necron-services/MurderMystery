package io.github.greenmc.murdermystery.arena.options;

import io.github.greenmc.murdermystery.Main;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public enum ArenaOption {

	TIMER(15),

	MINIMUM_PLAYERS(8),

	MAXIMUM_PLAYERS(16),

	CLASSIC_GAMEPLAY_TIME("", 600);

	int defaultValue;

	ArenaOption(int defaultValue) {
		this.defaultValue = defaultValue;
	}

	ArenaOption(String path, int defaultValue) {
		final Main plugin = JavaPlugin.getPlugin(Main.class);

		this.defaultValue = plugin.getConfig().getInt(path, defaultValue);
	}

	public int getDefaultValue() {
		return defaultValue;
	}
}