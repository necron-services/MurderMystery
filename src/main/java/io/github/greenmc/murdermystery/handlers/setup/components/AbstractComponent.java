package io.github.greenmc.murdermystery.handlers.setup.components;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import me.despical.commons.compat.XMaterial;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.item.ItemBuilder;
import me.despical.commons.serializer.LocationSerializer;
import me.despical.inventoryframework.pane.PaginatedPane;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class AbstractComponent {

	protected final ArenaEditorGUI gui;
	protected final Main plugin;
	protected final FileConfiguration config;

	protected static final ItemBuilder mainMenuItemBuilder =  new ItemBuilder(XMaterial.REDSTONE).name("&c&lReturn Main Menu").lore("&7Click to return last page!");

	public AbstractComponent(final ArenaEditorGUI gui) {
		this.gui = gui;
		this.plugin = gui.getPlugin();
		this.config = ConfigUtils.getConfig(plugin, "arena");
	}

	public abstract void registerComponents(final PaginatedPane paginatedPane);

	protected String isOptionDone(String path) {
		path = String.format("instance.%s.%s", this.gui.getArena().getId(), path);

		return config.isSet(path) ? "&a&l✔ Completed &7(value: &8" + config.getString(path) + "&7)" : "&c&l✘ Not Completed";
	}

	protected String isOptionDoneList(String path, int minimum) {
		path = String.format("instance.%s.%s", this.gui.getArena().getId(), path);

		if (config.isSet(path)) {
			int size = config.getStringList(path).size();

			return size < minimum ? "&c&l✘ Not Completed &c| &c&lPlease add more spawns" : "&a&l✔ Completed &7(value: &8" + size + "&7)";
		}

		return "&c&l✘ Not Completed";
	}

	protected String isOptionDoneBool(String path) {
		path = String.format("instance.%s.%s", this.gui.getArena().getId(), path);

		return config.isSet(path) ? LocationSerializer.isDefaultLocation(config.getString(path)) ? "&c&l✘ Not Completed" : "&a&l✔ Completed" : "&c&l✘ Not Completed";
	}

	protected boolean isOptionDoneBoolean(String path) {
		path = String.format("instance.%s.%s", this.gui.getArena().getId(), path);

		return config.isSet(path) && !LocationSerializer.isDefaultLocation(config.getString(path));
	}

	protected int getMinimumValueHigherThanZero(String path) {
		path = String.format("instance.%s.%s", this.gui.getArena().getId(), path);

		return Math.max(1, config.getInt(path));
	}

	protected void saveConfig() {
		ConfigUtils.saveConfig(plugin, this.config, "arena");
	}
}