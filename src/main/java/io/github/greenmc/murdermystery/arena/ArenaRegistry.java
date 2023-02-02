package io.github.greenmc.murdermystery.arena;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.user.User;
import me.despical.commons.configuration.ConfigUtils;
import me.despical.commons.serializer.LocationSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

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

	public ArenaRegistry(final @NotNull Main plugin) {
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
		this.arenas.clear();

		final FileConfiguration config = ConfigUtils.getConfig(plugin, "arena");
		final ConfigurationSection section = config.getConfigurationSection("instance");

		if (section == null) {
			plugin.getLogger().log(Level.WARNING, "Couldn't find 'instance' section in arena.yml, delete the file to regenerate it!");
			return;
		}

		for (final String id : section.getKeys(false)) {
			final String path = String.format("instance.%s.", id);
			final Arena arena = new Arena(id);

			this.registerArena(arena);

			arena.setReady(config.getBoolean(path + "ready"));
			arena.setMapName(config.getString(path + "mapName"));
			arena.setMinimumPlayers(config.getInt(path + "minimumPlayers"));
			arena.setMaximumPlayers(config.getInt(path + "maximumPlayers"));
			arena.setLobbyLocation(LocationSerializer.fromString(config.getString(path + "lobbyLocation")));
			arena.setEndLocation(LocationSerializer.fromString(config.getString(path + "endLocation")));
			arena.setPlayerSpawnPoints(config.getStringList(path + "playerSpawnPoints").stream().map(LocationSerializer::fromString).collect(Collectors.toList()));
			arena.setPlayerSpawnPoints(config.getStringList(path + "goldSpawnPoints").stream().map(LocationSerializer::fromString).collect(Collectors.toList()));

			if (!arena.isReady()) {
				plugin.getLogger().log(Level.WARNING, "Setup of arena {s} is not finished yet!", id);
				return;
			}
		}
	}
}