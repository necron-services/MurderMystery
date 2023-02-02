package io.github.greenmc.murdermystery.handlers.setup.components;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import me.despical.inventoryframework.pane.PaginatedPane;

public abstract class AbstractComponent {

	protected final ArenaEditorGUI gui;
	protected final Main plugin;

	public AbstractComponent(final ArenaEditorGUI gui) {
		this.gui = gui;
		this.plugin = gui.getPlugin();
	}

	public abstract void registerComponents(final PaginatedPane paginatedPane);
}