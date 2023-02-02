package io.github.greenmc.murdermystery.commands.command;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.commands.AbstractCommand;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

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
		
	}
}