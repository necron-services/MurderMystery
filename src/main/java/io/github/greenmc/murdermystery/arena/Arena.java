package io.github.greenmc.murdermystery.arena;

import io.github.greenmc.murdermystery.arena.managers.ScoreboardManager;
import io.github.greenmc.murdermystery.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public class Arena {

	private final String id;

	private final Set<User> players;

	private final ScoreboardManager scoreboardManager;

	public Arena(final @NotNull String id) {
		this.id = id;
		this.players = new HashSet<>();
		this.scoreboardManager = new ScoreboardManager(this);
	}

	@NotNull
	public String getId() {
		return this.id;
	}
}