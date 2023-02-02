package io.github.greenmc.murdermystery.handlers.setup;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.handlers.setup.components.component.MainMenuComponents;
import io.github.greenmc.murdermystery.user.User;
import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.pane.PaginatedPane;
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

	@NotNull
	private final PaginatedPane paginatedPane;

	public ArenaEditorGUI(final @NotNull Main plugin, final @NotNull User user) {
		this.plugin = plugin;
		this.user = user;
		this.gui = new Gui(plugin, 6, "MM Arena Editor");
		this.paginatedPane = new PaginatedPane(9, 6);
		this.gui.setOnGlobalClick(event -> event.setCancelled(true));
		this.gui.addPane(paginatedPane);

		this.injectGuiComponents();
	}

	private void injectGuiComponents() {
		final MainMenuComponents mainMenuComponents = new MainMenuComponents(this);
		mainMenuComponents.registerComponents(this.paginatedPane);
	}

	public void showGUI() {
		this.gui.show(this.user.getPlayer());
	}

	@NotNull
	public Main getPlugin() {
		return plugin;
	}
}