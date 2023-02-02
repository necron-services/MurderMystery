package io.github.greenmc.murdermystery.arena;

import io.github.greenmc.murdermystery.arena.managers.ScoreboardManager;
import io.github.greenmc.murdermystery.user.User;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public class Arena extends BukkitRunnable {

	private final String id;

	private final Set<User> players;

	private final ScoreboardManager scoreboardManager;

	private ArenaState arenaState = ArenaState.INACTIVE;

	public Arena(final @NotNull String id) {
		this.id = id;
		this.players = new HashSet<>();
		this.scoreboardManager = new ScoreboardManager(this);
	}

	@Override
	public void run() {

	}

	public boolean isInArena(final User user) {
		return user != null && this.players.contains(user);
	}

	public ArenaState getArenaState() {
		return this.arenaState;
	}

	public void setArenaState(final ArenaState arenaState) {
		this.arenaState = arenaState;
	}

	@NotNull
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return String.format("name=%s", this.id);
	}
}