package io.github.greenmc.murdermystery.commands;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.commands.command.AdminCommands;

public abstract class AbstractCommand {

	protected final Main plugin;

	public AbstractCommand(final Main plugin) {
		this.plugin = plugin;
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