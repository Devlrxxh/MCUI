package dev.lrxh.mCUI.example;

import dev.lrxh.mCUI.component.UIComponent;
import dev.lrxh.mCUI.elements.DefaultUIProvider;
import dev.lrxh.mCUI.elements.Element;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class UIComponentExample extends UIComponent {
    private final Element element;

    public UIComponentExample(JavaPlugin plugin) {
        element = getUi().register(27, 2, new File(plugin.getDataFolder(), "images/icon.png"));
        getUi().load();
    }

    @Override
    public void tick(Player player) {
        DefaultUIProvider.ACTION_BAR.render(player, element);
    }
}
