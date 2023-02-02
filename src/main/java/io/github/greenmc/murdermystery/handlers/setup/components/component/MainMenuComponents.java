package io.github.greenmc.murdermystery.handlers.setup.components.component;

import io.github.greenmc.murdermystery.handlers.setup.ArenaEditorGUI;
import io.github.greenmc.murdermystery.handlers.setup.components.AbstractComponent;
import me.despical.commons.compat.XMaterial;
import me.despical.inventoryframework.GuiItem;
import me.despical.inventoryframework.pane.PaginatedPane;
import me.despical.inventoryframework.pane.StaticPane;

/**
 * @author Despical
 * <p>
 * Created at 2.02.2023
 */
public class MainMenuComponents extends AbstractComponent {

	public MainMenuComponents(ArenaEditorGUI gui) {
		super (gui);
	}

	@Override
	public void registerComponents(PaginatedPane paginatedPane) {
		final StaticPane pane = new StaticPane(9, 6);
		pane.fillProgressBorder(GuiItem.of(XMaterial.LIME_STAINED_GLASS_PANE.parseItem()), GuiItem.of(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()), 0);

		paginatedPane.addPane(0, pane);
	}
}