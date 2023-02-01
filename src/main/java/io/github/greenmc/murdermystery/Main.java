package io.github.greenmc.murdermystery;

import io.github.greenmc.murdermystery.events.EventListener;
import io.github.greenmc.murdermystery.user.UserManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {

	@NotNull
	private UserManager userManager;

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

		EventListener.registerEvents(this);
	}

	public UserManager getUserManager() {
		return userManager;
	}
}