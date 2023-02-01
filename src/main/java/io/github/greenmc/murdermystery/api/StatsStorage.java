package io.github.greenmc.murdermystery.api;

import org.jetbrains.annotations.NotNull;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public class StatsStorage {

	public enum StatisticType {

		;


		final String statisticName;
		final boolean persistent;

		StatisticType(String statisticName, boolean persistent) {
			this.statisticName = statisticName;
			this.persistent = persistent;
		}

		@NotNull
		public String getStatisticName() {
			return statisticName;
		}

		public boolean isPersistent() {
			return persistent;
		}
	}
}