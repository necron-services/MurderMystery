package io.github.greenmc.murdermystery.events;

import io.github.greenmc.murdermystery.Main;
import io.github.greenmc.murdermystery.events.event.*;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Despical
 * <p>
 * Created at 1.02.2023
 */
public abstract class EventListener implements Listener {

	@NotNull
	protected final Main plugin;

	public EventListener(@NotNull Main plugin) {
		this.plugin = plugin;
	}

	public static void registerEvents(final Main plugin) {
		final Class<?>[] listenerAdapters = {JoinQuitEvents.class};

		try {
			for (Class<?> listenerAdapter : listenerAdapters) {
				listenerAdapter.getConstructor(Main.class).newInstance(plugin);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}