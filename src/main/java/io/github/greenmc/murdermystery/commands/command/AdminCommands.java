package io.github.greenmc.murdermystery.commands.command;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.arena.Arena;
import io.github.greenmc.murdermystery.commands.AbstractCommand;
import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import io.github.greenmc.murdermystery.user.User;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

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
			user.sendMessage("can not do that in-game message");
			return;
		}

		if (arguments.isArgumentsEmpty()) {
			user.sendMessage("no arg provided message");
			return;
		}

		final String arenaId = arguments.getArgument(0);

		if (plugin.getArenaRegistry().isArena(arenaId)) {
			user.sendMessage("arena already created message");
			return;
		}

		final Arena arena = new Arena(arenaId);

		// implement arena data

		plugin.getArenaRegistry().registerArena(arena);

		user.sendMessage("arena created message");
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
			user.sendMessage("can not do that in-game message");
			return;
		}

		if (arguments.isArgumentsEmpty()) {
			user.sendMessage("no arg provided message");
			return;
		}

		final String arenaId = arguments.getArgument(0);

		if (plugin.getArenaRegistry().isArena(arenaId)) {
			user.sendMessage("no arena found message");
			return;
		}

		final Arena arena = plugin.getArenaRegistry().getArena(arenaId);

		// delete arena configuration and stop it if needed when ArenaManager done

		plugin.getArenaRegistry().unregisterArena(arena);

		user.sendMessage("arena deleted message");
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

		new ArenaEditorGUI(plugin, user).showGUI();
	}
}