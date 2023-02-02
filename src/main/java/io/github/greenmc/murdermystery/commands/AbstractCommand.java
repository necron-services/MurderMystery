package io.github.greenmc.murdermystery.commands;

import io.github.greenmc.murdermystery.Main;

public abstract class AbstractCommand {

	protected final Main plugin;

	public AbstractCommand(final Main plugin) {
		this.plugin = plugin;
		this.plugin.getCommandFramework().registerCommands(this);
	}
}