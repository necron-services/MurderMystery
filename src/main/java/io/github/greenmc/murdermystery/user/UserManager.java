package io.github.greenmc.murdermystery.user;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.api.StatsStorage;
import io.github.greenmc.murdermystery.user.data.FileStatistics;
import io.github.greenmc.murdermystery.user.data.IUserDatabase;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public class UserManager {

	@NotNull
	private final Set<User> users;

	@NotNull
	private final Map<StatsStorage.StatisticType, Integer> statistics;

	@NotNull
	private final IUserDatabase userDatabase;

	public UserManager(Main plugin) {
		this.users = new HashSet<>();
		this.statistics = new HashMap<>();
		this.userDatabase = new FileStatistics(plugin);
	}

	@NotNull
	public User addUser(final Player player) {
		final User user = new User(player);

		this.users.add(user);
		return user;
	}

	public void removeUser(final Player player) {
		this.users.remove(this.getUser(player));
	}

	@NotNull
	public User getUser(final Player player) {
		final UUID uuid = player.getUniqueId();

		for (User user : this.users) {
			if (uuid.equals(user.getUniqueId())) {
				return user;
			}
		}

		return this.addUser(player);
	}

	@NotNull
	public Set<User> getUsers() {
		return Set.copyOf(users);
	}

	public void addStatistic(StatsStorage.StatisticType statisticType, int value) {
		this.setStatistic(statisticType, this.getStatistic(statisticType) + value);
	}

	public void setStatistic(StatsStorage.StatisticType statisticType, int value) {
		this.statistics.put(statisticType, value);
	}

	public void loadStatistics(final Player player) {
		this.userDatabase.loadStatistics(this.getUser(player));
	}

	public int getStatistic(StatsStorage.StatisticType statisticType) {
		final Integer statistic = this.statistics.get(statisticType);

		if (statistic == null) {
			this.statistics.put(statisticType, 0);
			return 0;
		}

		return statistic;
	}
}