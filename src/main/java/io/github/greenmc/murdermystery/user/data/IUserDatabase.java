package io.github.greenmc.murdermystery.user.data;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.api.StatsStorage;
import io.github.greenmc.murdermystery.user.User;
import org.jetbrains.annotations.NotNull;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public abstract sealed class IUserDatabase permits FileStatistics, MysqlDatabase {

	@NotNull
	protected final Main plugin;

	public IUserDatabase(final Main plugin) {
		this.plugin = plugin;
	}

	public abstract void saveStatistic(final @NotNull User user, final StatsStorage.StatisticType statisticType);

	public abstract void saveStatistics(final @NotNull User user);

	public abstract void loadStatistics(final @NotNull User user);
}