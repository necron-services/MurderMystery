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
public non-sealed class FileStatistics extends IUserDatabase {

	public FileStatistics(Main plugin) {
		super (plugin);
	}

	@Override
	public void saveStatistic(@NotNull User user, StatsStorage.StatisticType statisticType) {

	}

	@Override
	public void saveStatistics(@NotNull User user) {

	}

	@Override
	public void loadStatistics(@NotNull User user) {

	}
}