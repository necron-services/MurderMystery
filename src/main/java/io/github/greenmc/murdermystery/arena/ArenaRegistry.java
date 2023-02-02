package io.github.greenmc.murdermystery.arena;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Despical
 * <p>
 * Created at 2.02.2023
 */
public class ArenaRegistry {

	@NotNull
	private final Main plugin;

	@NotNull
	private final Set<Arena> arenas;

	public ArenaRegistry(final Main plugin) {
		this.plugin = plugin;
		this.arenas = new HashSet<>();

		this.registerArenas();
	}

	public void registerArena(final Arena arena) {
		this.arenas.add(arena);
	}

	public void unregisterArena(final Arena arena) {
		this.arenas.remove(arena);
	}

	@NotNull
	public Set<Arena> getArenas() {
		return Set.copyOf(arenas);
	}

	@Nullable
	public Arena getArena(final String id) {
		if (id == null) return null;

		return this.arenas.stream().filter(arena -> arena.getId().equals(id)).findFirst().orElse(null);
	}

	@Nullable
	public Arena getArena(final User user) {
		if (user == null) return null;

		return this.arenas.stream().filter(arena -> arena.isInArena(user)).findFirst().orElse(null);
	}

	public boolean isArena(final String arenaId) {
		return arenaId != null && getArena(arenaId) != null;
	}

	public boolean isInArena(final User user) {
		return this.getArena(user) != null;
	}

	private void registerArenas() {

	}
}