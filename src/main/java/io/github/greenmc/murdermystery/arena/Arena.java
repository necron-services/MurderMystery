package io.github.greenmc.murdermystery.arena;

import io.github.greenmc.murdermystery.arena.managers.ScoreboardManager;
import io.github.greenmc.murdermystery.arena.options.ArenaOption;
import io.github.greenmc.murdermystery.user.User;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public class Arena {

	private final String id;

	private final List<User> players, murderers, detectives, spectators, deaths;

	private final Map<ArenaOption, Integer> arenaOptions;
	private final Map<GameLocation, Location> gameLocations;

	private final ScoreboardManager scoreboardManager;

	private boolean ready;
	private String mapName;
	private ArenaState arenaState = ArenaState.INACTIVE;

	public Arena(final @NotNull String id) {
		this.id = id;
		this.mapName = id;
		this.players = new ArrayList<>();
		this.murderers = new ArrayList<>();
		this.detectives = new ArrayList<>();
		this.spectators = new ArrayList<>();
		this.deaths = new ArrayList<>();
		this.arenaOptions = new EnumMap<>(ArenaOption.class);
		this.gameLocations = new EnumMap<>(GameLocation.class);
		this.scoreboardManager = new ScoreboardManager(this);

		for (ArenaOption option : ArenaOption.values()) {
			arenaOptions.put(option, option.getDefaultValue());
		}
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

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getSetupProgress() {
		return ready ? 100 : 0;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public int getTimer() {
		return getOption(ArenaOption.TIMER);
	}

	public void setTimer(int timer) {
		setOptionValue(ArenaOption.TIMER, timer);
	}

	public int getMaximumPlayers() {
		return getOption(ArenaOption.MAXIMUM_PLAYERS);
	}

	public void setMaximumPlayers(int maximumPlayers) {
		setOptionValue(ArenaOption.MAXIMUM_PLAYERS, maximumPlayers);
	}

	public int getMinimumPlayers() {
		return getOption(ArenaOption.MINIMUM_PLAYERS);
	}

	public void setMinimumPlayers(int minimumPlayers) {
		setOptionValue(ArenaOption.MINIMUM_PLAYERS, minimumPlayers);
	}

	public Location getLobbyLocation() {
		return gameLocations.get(GameLocation.LOBBY);
	}

	public void setLobbyLocation(Location lobbyLocation) {
		gameLocations.put(GameLocation.LOBBY, lobbyLocation);
	}

	public Location getEndLocation() {
		return gameLocations.get(GameLocation.END);
	}

	public void setEndLocation(Location endLocation) {
		gameLocations.put(GameLocation.END, endLocation);
	}

	private int getOption(ArenaOption option) {
		return arenaOptions.get(option);
	}

	private void setOptionValue(ArenaOption option, int value) {
		arenaOptions.put(option, value);
	}

	@NotNull
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return String.format("name=%s", this.id);
	}

	public enum GameLocation {
		LOBBY, END
	}
}