package io.github.greenmc.murdermystery.handlers.setup.components.component;

import io.github.greenmc.murdermystery.arena.Arena;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import io.github.greenmc.murdermystery.handlers.setup.components.AbstractComponent;
import io.github.greenmc.murdermystery.user.User;
import me.despical.commons.compat.XMaterial;
import me.despical.commons.serializer.LocationSerializer;
import me.despical.inventoryframework.GuiItem;
import me.despical.inventoryframework.pane.PaginatedPane;
import me.despical.inventoryframework.pane.StaticPane;
import org.bukkit.Location;

/**
 * @author Despical
 * <p>
 * Created at 2.02.2023
 */
public class LobbyLocationComponents extends AbstractComponent {

	public LobbyLocationComponents(ArenaEditorGUI gui) {
		super (gui);
	}

	@Override
	public void registerComponents(PaginatedPane paginatedPane) {
		final Arena arena = this.gui.getArena();
		final User user = this.gui.getUser();
		final String path = String.format("instance.%s.", arena.getId());
		final StaticPane pane = new StaticPane(9, 3);

		pane.fillWith(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), event -> event.setCancelled(true));
		pane.addItem(GuiItem.of(XMaterial.REDSTONE.parseItem(), event -> this.gui.restorePage()), 8, 2);

		pane.addItem(GuiItem.of(XMaterial.LIME_CONCRETE.parseItem(), event -> {
			final Location location = user.getPlayer().getLocation();

			if (!event.isShiftClick()) user.getPlayer().closeInventory();

			config.set(path + "lobbyLocation", LocationSerializer.toString(location));
			super.saveConfig();

			arena.setLobbyLocation(location);

			user.sendRawMessage("Lobby location is set as your location.");
		}), 3, 1);

		pane.addItem(GuiItem.of(XMaterial.BLUE_CONCRETE.parseItem(), event -> {
			final Location location = user.getPlayer().getLocation();

			if (!event.isShiftClick()) user.getPlayer().closeInventory();

			config.set(path + "endLocation", LocationSerializer.toString(location));
			super.saveConfig();

			arena.setEndLocation(location);

			user.sendRawMessage("End location is set as your location.");
		}), 5, 1);

		paginatedPane.addPane(1, pane);
	}
}