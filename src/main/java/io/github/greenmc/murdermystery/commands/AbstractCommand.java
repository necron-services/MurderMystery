package io.github.greenmc.murdermystery.commands;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.commands.command.AdminCommands;
import me.despical.commons.configuration.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AbstractCommand {

	protected final Main plugin;
	protected final FileConfiguration arenaConfig;

	public AbstractCommand(final Main plugin) {
		this.plugin = plugin;
		this.arenaConfig = ConfigUtils.getConfig(plugin, "arena");
		this.plugin.getCommandFramework().registerCommands(this);
	}

	public static void registerCommands(final Main plugin) {
		final Class<?>[] commandClasses = new Class[] {AdminCommands.class};

		for (Class<?> clazz : commandClasses) {
			try {
				clazz.getConstructor(Main.class).newInstance(plugin);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}