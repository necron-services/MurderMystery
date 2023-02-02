package io.github.greenmc.murdermystery;

import io.github.greenmc.murdermystery.arena.ArenaRegistry;
import io.github.greenmc.murdermystery.commands.AbstractCommand;
import io.github.greenmc.murdermystery.events.EventListener;
import io.github.greenmc.murdermystery.user.UserManager;
import me.despical.commandframework.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Main extends JavaPlugin {

	private UserManager userManager;
	private CommandFramework commandFramework;
	private ArenaRegistry arenaRegistry;

	@Override
	public void onEnable() {
		this.initializeClasses();
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	private void initializeClasses() {
		this.setupConfigurationFiles();

		this.userManager = new UserManager(this);
		this.commandFramework = new CommandFramework(this);
		this.arenaRegistry = new ArenaRegistry(this);

		EventListener.registerEvents(this);
		AbstractCommand.registerCommands(this);
	}

	private void setupConfigurationFiles() {
		this.saveDefaultConfig();

		Set.of("arena").forEach(fileName -> this.saveResource(fileName + ".yml", false));
	}

	@NotNull
	public UserManager getUserManager() {
		return userManager;
	}

	@NotNull
	public CommandFramework getCommandFramework() {
		return commandFramework;
	}

	@NotNull
	public ArenaRegistry getArenaRegistry() {
		return arenaRegistry;
	}
}