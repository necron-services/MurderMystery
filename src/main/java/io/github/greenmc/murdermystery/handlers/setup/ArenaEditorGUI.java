package io.github.greenmc.murdermystery.handlers.setup;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.arena.Arena;
import io.github.greenmc.murdermystery.handlers.setup.components.component.LobbyLocationComponents;
import io.github.greenmc.murdermystery.handlers.setup.components.component.MainMenuComponents;
import io.github.greenmc.murdermystery.user.User;
import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.pane.PaginatedPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	private final Arena arena;

	@NotNull
	private final PaginatedPane paginatedPane;

	@Nullable
	private LastPageCache lastPageCache;

	public ArenaEditorGUI(final @NotNull Main plugin, final @NotNull User user, final @NotNull Arena arena) {
		this.plugin = plugin;
		this.user = user;
		this.arena = arena;
		this.gui = new Gui(plugin, 4, "MM Arena Editor");
		this.paginatedPane = new PaginatedPane(9, 4);
		this.gui.setOnGlobalClick(event -> event.setCancelled(true));
		this.gui.addPane(paginatedPane);

		this.injectGuiComponents();
	}

	private void injectGuiComponents() {
		final MainMenuComponents mainMenuComponents = new MainMenuComponents(this);
		mainMenuComponents.registerComponents(this.paginatedPane);

		final LobbyLocationComponents lobbyLocationComponents = new LobbyLocationComponents(this);
		lobbyLocationComponents.registerComponents(this.paginatedPane);
	}

	public void showGUI() {
		this.gui.show(this.user.getPlayer());
	}

	public void setPage(@Nullable String title, int rows, int page) {
		this.lastPageCache = new LastPageCache(this, gui.getTitle(), gui.getRows(), paginatedPane.getPage());

		this.gui.setTitle(title != null ? title : this.gui.getTitle());
		this.gui.setRows(rows);
		this.paginatedPane.setPage(page);

		this.gui.update();
	}

	public void restorePage() {
		if (this.lastPageCache != null) this.lastPageCache.restore();
	}

	@NotNull
	public Main getPlugin() {
		return plugin;
	}

	@NotNull
	public User getUser() {
		return user;
	}

	@NotNull
	public Gui getGui() {
		return gui;
	}

	@NotNull
	public Arena getArena() {
		return arena;
	}

	private record LastPageCache(ArenaEditorGUI editorGUI, String title, int rows, int page) {

		void restore() {
			editorGUI.paginatedPane.setPage(page);
			editorGUI.gui.setRows(rows);
			editorGUI.gui.setTitle(title);
			editorGUI.gui.update();
		}
	}
}