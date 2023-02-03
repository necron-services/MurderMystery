package io.github.greenmc.murdermystery.handlers.setup.components.component;

import io.github.greenmc.murdermystery.arena.Arena;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import io.github.greenmc.murdermystery.handlers.setup.components.AbstractComponent;
import io.github.greenmc.murdermystery.user.User;
import me.despical.commons.compat.XMaterial;
import me.despical.commons.item.ItemBuilder;
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
		final boolean backgroundDone = isOptionDoneBoolean("lobbyLocation") && isOptionDoneBoolean("endLocation");

		final ItemBuilder backgroundItem = backgroundDone ?
				new ItemBuilder(XMaterial.LIME_STAINED_GLASS_PANE).name("&aGame locations set properly!") :
				new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).name("&cSet game locations properly!");

		final ItemBuilder endLocationItem = new ItemBuilder(XMaterial.ORANGE_CONCRETE)
				.name("&e&lSet Ending Location")
				.lore("&7Click to set the ending location")
				.lore("&7on place where you are standing.")
				.lore("&8(location where players will be")
				.lore("&8teleported after the game ended)")
				.lore("", isOptionDoneBool("endLocation"));

		final ItemBuilder lobbyLocationItem = new ItemBuilder(XMaterial.CYAN_CONCRETE)
				.name("&e&lSet Lobby Location")
				.lore("&7Click to set lobby location")
				.lore("&7on place where you are standing.")
				.lore("&8(location where players will be")
				.lore("&8teleported before the game starts)")
				.lore("", isOptionDoneBool("lobbyLocation"));

		pane.fillWith(backgroundItem.build(), event -> event.setCancelled(true));
		pane.addItem(GuiItem.of(mainMenuItemBuilder.build(), event -> this.gui.restorePage()), 8, 2);

		pane.addItem(GuiItem.of(lobbyLocationItem.build(), event -> {
			final Location location = user.getLocation();

			if (!event.isShiftClick()) user.getPlayer().closeInventory();

			config.set(path + "lobbyLocation", LocationSerializer.toString(location));
			super.saveConfig();

			arena.setLobbyLocation(location);

			user.sendRawMessage("&e✔ Completed | &aLobby location for arena &e" + arena.getId() + " &aset at your location!");
		}), 3, 1);

		pane.addItem(GuiItem.of(endLocationItem.build(), event -> {
			final Location location = user.getLocation();

			if (!event.isShiftClick()) user.getPlayer().closeInventory();

			config.set(path + "endLocation", LocationSerializer.toString(location));
			super.saveConfig();

			arena.setEndLocation(location);

			user.sendRawMessage("&e✔ Completed | &aEnding location for arena &e" + arena.getId() + " &aset at your location!");
		}), 5, 1);

		paginatedPane.addPane(1, pane);
	}
}