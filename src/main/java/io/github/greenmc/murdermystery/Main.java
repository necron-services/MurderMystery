package io.github.greenmc.murdermystery;

import io.github.greenmc.murdermystery.arena.ArenaRegistry;
import io.github.greenmc.murdermystery.events.EventListener;
import io.github.greenmc.murdermystery.user.UserManager;
import me.despical.commandframework.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {

	@NotNull
	private UserManager userManager;

	@NotNull
	private CommandFramework commandFramework;

	@NotNull
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
		this.userManager = new UserManager(this);
		this.commandFramework = new CommandFramework(this);
		this.arenaRegistry = new ArenaRegistry(this);

		EventListener.registerEvents(this);
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