package io.github.greenmc.murdermystery.events.event;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.events.EventListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public class JoinQuitEvents extends EventListener {

	public JoinQuitEvents(Main plugin) {
		super (plugin);
	}

	@EventHandler
	public void onJoinEvent(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		plugin.getUserManager().loadStatistics(player);
	}

	@EventHandler
	public void onQuitEvent(final PlayerQuitEvent event) {
		this.handleQuitEvent(event.getPlayer());
	}

	@EventHandler
	public void onKickEvent(final PlayerKickEvent event) {
		this.handleQuitEvent(event.getPlayer());
	}

	private void handleQuitEvent(final Player eventPlayer) {
		// remove player from arena logic

		plugin.getUserManager().removeUser(eventPlayer);
	}
}