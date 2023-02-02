package io.github.greenmc.murdermystery.user;

import me.despical.commons.util.Strings;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public class User {

	private final Player player;

	public User(Player player) {
		this.player = player;
	}

	public void sendMessage(final String path) {
		this.sendRawMessage(path); // not implemented yet
	}

	public void sendRawMessage(final String message) {
		this.player.sendMessage(Strings.format(message));
	}

	public Player getPlayer() {
		return this.player;
	}

	public UUID getUniqueId() {
		return this.player.getUniqueId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User other)) return false;

		return other.getUniqueId().equals(this.getUniqueId());
	}

	@Override
	public String toString() {
		return String.format("name=%s, uuid=%s", player.getName(), player.getUniqueId());
	}
}