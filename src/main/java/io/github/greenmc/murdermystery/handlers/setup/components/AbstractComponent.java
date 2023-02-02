package io.github.greenmc.murdermystery.handlers.setup.components;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.inventoryframework.pane.PaginatedPane;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AbstractComponent {

	protected final ArenaEditorGUI gui;
	protected final Main plugin;
	protected final FileConfiguration config;

	public AbstractComponent(final ArenaEditorGUI gui) {
		this.gui = gui;
		this.plugin = gui.getPlugin();
		this.config = ConfigUtils.getConfig(plugin, "arena");
	}

	protected void saveConfig() {
		ConfigUtils.saveConfig(plugin, this.config, "arena");
	}

	public abstract void registerComponents(final PaginatedPane paginatedPane);
}