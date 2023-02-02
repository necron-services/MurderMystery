package io.github.greenmc.murdermystery.handlers.setup;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.user.User;
import me.despical.inventoryframework.Gui;
import org.jetbrains.annotations.NotNull;

/**
 * @author Despical
 * <p>
 * Created at 2.02.2023
 */
public class ArenaEditorGUI {

	@NotNull
	private final User user;

	@NotNull
	private final Main plugin;

	@NotNull
	private final Gui gui;

	public ArenaEditorGUI(final Main plugin, final User user) {
		this.plugin = plugin;
		this.user = user;
		this.gui = new Gui(plugin, 6, "MM Arena Editor");
		this.gui.setOnGlobalClick(event -> event.setCancelled(true));
	}

	public void showGUI() {
		this.gui.show(this.user.getPlayer());
	}
}