package io.github.greenmc.murdermystery.commands.command;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.arena.Arena;
import io.github.greenmc.murdermystery.commands.AbstractCommand;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import io.github.greenmc.murdermystery.user.User;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.serializer.LocationSerializer;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static me.despical.commandframework.Command.SenderType.*;

/**
 * @author Despical
 * <p>
 * Created at 2.02.2023
 */
public class AdminCommands extends AbstractCommand {

	public AdminCommands(Main plugin) {
		super (plugin);
	}

	@Command(
			name = "mm"
	)
	public void mmCommand(CommandArguments arguments) {
		arguments.sendMessage("mm command");
	}

	@Command(
			name = "mm.create",
			permission = "mm.admin.create",
			desc = "Create an arena with default configuration.",
			usage = "/mm create <arena name>",
			senderType = PLAYER
	)
	public void mmCreateCommand(CommandArguments arguments) {
		final User user = plugin.getUserManager().getUser(arguments.getSender());

		if (plugin.getArenaRegistry().isInArena(user)) {
			user.sendMessage("admin-commands.cannot-do-that-ingame");
			return;
		}

		if (arguments.isArgumentsEmpty()) {
			user.sendRawMessage("admin-commands.provide-an-arena-name");
			return;
		}

		final String arenaId = arguments.getArgument(0);

		if (plugin.getArenaRegistry().isArena(arenaId)) {
			user.sendMessage("admin-commands.there-is-already-an-arena");
			return;
		}

		final String path = String.format("instance.%s.", arenaId);
		final Arena arena = new Arena(arenaId);

		plugin.getArenaRegistry().registerArena(arena);

		arenaConfig.set(path + "mapName", arenaId);
		arenaConfig.set(path + "minimumPlayers", 8);
		arenaConfig.set(path + "maximumPlayers", 16);
		arenaConfig.set(path + "ready", false);
		arenaConfig.set(path + "lobbyLocation", LocationSerializer.SERIALIZED_LOCATION);
		arenaConfig.set(path + "endLocation", LocationSerializer.SERIALIZED_LOCATION);
		arenaConfig.set(path + "playerSpawnPoints", new ArrayList<>());
		arenaConfig.set(path + "goldSpawnPoints", new ArrayList<>());

		ConfigUtils.saveConfig(plugin, arenaConfig, "arena");

		user.sendRawMessage("arena created message");
	}

	@Command(
			name = "mm.delete",
			permission = "mm.admin.delete",
			desc = "Delete specified arena and its data",
			usage = "/mm delete <arena name>",
			senderType = PLAYER
	)
	public void mmDeleteCommand(CommandArguments arguments) {
		final User user = plugin.getUserManager().getUser(arguments.getSender());

		if (plugin.getArenaRegistry().isInArena(user)) {
			user.sendMessage("admin-commands.cannot-do-that-ingame");
			return;
		}

		if (arguments.isArgumentsEmpty()) {
			user.sendRawMessage("admin-commands.provide-an-arena-name");
			return;
		}

		final String arenaId = arguments.getArgument(0);

		if (!plugin.getArenaRegistry().isArena(arenaId)) {
			user.sendMessage("admin-commands.no-arena-found-with-that-name");
			return;
		}

		final Arena arena = plugin.getArenaRegistry().getArena(arenaId);

		arenaConfig.set("instances." + arenaId, null);
		ConfigUtils.saveConfig(plugin, arenaConfig, "arena");

		plugin.getArenaRegistry().unregisterArena(arena);

		user.sendMessage("admin-commands.deleted-arena-successfully", arenaId);
	}

	@Command(
			name = "mm.list",
			permission = "mm.admin.list",
			desc = "Get a list of registered arenas and their status",
			usage = "/mm list",
			senderType = PLAYER
	)
	public void mmListCommand(CommandArguments arguments) {
		final User user = plugin.getUserManager().getUser(arguments.getSender());

		user.sendRawMessage(plugin.getArenaRegistry().getArenas().stream().map(Arena::getId).collect(Collectors.joining(", ")));
	}

	@Command(
			name = "mm.edit",
			permission = "mm.admin.edit",
			desc = "Open arena editor for specified arena",
			usage = "/mm edit <arena name>",
			senderType = PLAYER
	)
	public void mmEditCommand(CommandArguments arguments) {
		final User user = plugin.getUserManager().getUser(arguments.getSender());

		if (arguments.isArgumentsEmpty()) {
			user.sendMessage("admin-commands.provide-an-arena-name");
			return;
		}

		final Arena arena = plugin.getArenaRegistry().getArena(arguments.getArgument(0));

		if (arena == null) {
			user.sendMessage("admin-commands.no-arena-found-with-that-name");
			return;
		}

		new ArenaEditorGUI(plugin, user, arena).showGUI();
	}
}