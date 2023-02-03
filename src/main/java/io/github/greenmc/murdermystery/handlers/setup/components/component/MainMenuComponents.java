package io.github.greenmc.murdermystery.handlers.setup.components.component;

import io.github.greenmc.murdermystery.arena.Arena;
import io.github.greenmc.murdermystery.arena.ArenaState;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import io.github.greenmc.murdermystery.handlers.setup.components.AbstractComponent;
import io.github.greenmc.murdermystery.user.User;
import me.despical.commons.compat.XMaterial;
import me.despical.commons.item.ItemBuilder;
import me.despical.commons.serializer.LocationSerializer;
import me.despical.commons.util.Strings;
import me.despical.commons.util.conversation.ConversationBuilder;
import me.despical.inventoryframework.Gui;
import me.despical.inventoryframework.GuiItem;
import me.despical.inventoryframework.pane.PaginatedPane;
import me.despical.inventoryframework.pane.StaticPane;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * @author Despical
 * <p>
 * Created at 2.02.2023
 */
public class MainMenuComponents extends AbstractComponent {

	public MainMenuComponents(ArenaEditorGUI gui) {
		super (gui);
	}

	@Override
	public void registerComponents(PaginatedPane paginatedPane) {
		final Gui gui = this.gui.getGui();
		final User user = this.gui.getUser();
		final Arena arena = this.gui.getArena();
		final Player player = user.getPlayer();
		final StaticPane pane = new StaticPane(9, 4);

		pane.fillWith(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());
		pane.fillProgressBorder(GuiItem.of(XMaterial.LIME_STAINED_GLASS_PANE.parseItem()), GuiItem.of(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()), 0);
		pane.addItem(GuiItem.of(XMaterial.WHITE_CONCRETE.parseItem(), event -> this.gui.setPage("Set LOBBY and END locations", 3, 1)), 1, 1);
		pane.addItem(GuiItem.of(XMaterial.BLACK_CONCRETE.parseItem(), event -> this.gui.setPage("Add GOLD and PLAYER spawn point", 3, 2)), 5, 1);
		pane.addItem(GuiItem.of(XMaterial.RED_CONCRETE.parseItem(), event -> this.gui.setPage("Set MIN and MAX player amount", 3, 3)), 7, 1);

		pane.addItem(GuiItem.of(XMaterial.NAME_TAG.parseItem(), event -> {
			player.closeInventory();

			new ConversationBuilder(plugin).withPrompt(new StringPrompt() {

				@Override
				@NotNull
				public String getPromptText(@NotNull ConversationContext context) {
					return Strings.format("&ePlease type in chat arena name. You can use color codes.");
				}

				@Override
				public Prompt acceptInput(@NotNull ConversationContext context, String input) {
					String name = Strings.format(input);
					user.sendRawMessage("&e✔ Completed | &aName of arena &e" + arena.getId() + " &aset to &e" + name);

					arena.setMapName(name);

					config.set("instance." + arena.getId() + ".mapName", arena.getMapName());
					saveConfig();

					return Prompt.END_OF_CONVERSATION;
				}
			}).buildFor(player);
		}), 3, 1);

		ItemStack registerItem;

		if (arena.isReady()) {
			registerItem = new ItemBuilder(XMaterial.BARRIER)
					.name("&a&lArena Registered - Congratulations")
					.lore("&7This arena is already registered!")
					.lore("&7Good job, you went through whole setup!")
					.lore("&7You can play on this arena now!")
					.enchantment(Enchantment.DURABILITY)
					.flag(ItemFlag.HIDE_ENCHANTS)
					.build();
		} else {
			registerItem = new ItemBuilder(XMaterial.FIREWORK_ROCKET)
					.name("&e&lFinish Setup")
					.lore("&7Click this when you're done with configuration.")
					.lore("&7It will validate and register the arena.")
					.build();
		}

		pane.addItem(GuiItem.of(registerItem, e -> {
			player.closeInventory();

			if (arena.isReady()) {
				user.sendRawMessage("&a&l✔ &aThis arena was already validated and is ready to use!");
				return;
			}

			String path = "instance." + arena.getId() + ".", locations[] = {"lobbyLocation", "endLocation"}, spawns[] = {"playersSpawnPoints", "goldSpawnPoints"};

			for (String location : locations) {
				if (!config.isSet(path + location) || LocationSerializer.isDefaultLocation(config.getString(path + location))) {
					user.sendRawMessage("&c&l✘ &cArena validation failed! Please configure following spawn properly: " + location + " (cannot be world spawn location)");
					return;
				}
			}

			for (String spawn : spawns) {
				if (!config.isSet(path + spawn) || config.getStringList(path + spawn).size() < arena.getMaximumPlayers()) {
					user.sendRawMessage("&c&l✘ &cArena validation failed! Please configure following spawns properly: " + spawn + " (must be minimum " + arena.getMaximumPlayers() + " spawns)");
					return;
				}
			}

			user.sendRawMessage("&a&l✔ &aValidation succeeded! Registering new arena instance: " + arena.getId());

			config.set(path + "ready", true);
			saveConfig();

			arena.setReady(true);
			arena.setArenaState(ArenaState.WAITING_FOR_PLAYERS);
			arena.setMapName(config.getString(path + "mapName"));
			arena.setMinimumPlayers(config.getInt(path + "minimumPlayers"));
			arena.setMaximumPlayers(config.getInt(path + "maximumPlayers"));
			arena.setLobbyLocation(LocationSerializer.fromString(config.getString(path + "lobbyLocation")));
			arena.setEndLocation(LocationSerializer.fromString(config.getString(path + "endLocation")));
			arena.setPlayerSpawnPoints(config.getStringList(path + "playersSpawnPoints").stream().map(LocationSerializer::fromString).collect(Collectors.toList()));
			arena.setGoldSpawnPoints(config.getStringList(path + "goldSpawnPoints").stream().map(LocationSerializer::fromString).collect(Collectors.toList()));
		}), 8, 3);

		paginatedPane.addPane(0, pane);
	}
}