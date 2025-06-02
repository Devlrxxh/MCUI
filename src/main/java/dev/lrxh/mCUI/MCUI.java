package dev.lrxh.mCUI;

import dev.lrxh.mCUI.component.ComponentManager;
import dev.lrxh.mCUI.component.ComponentRunnable;
import dev.lrxh.mCUI.elements.Element;
import dev.lrxh.mCUI.elements.UI;
import dev.lrxh.mCUI.component.UIComponent;
import dev.lrxh.mCUI.example.UIComponentExample;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCUI extends JavaPlugin implements Listener {
    private ComponentManager componentManager;
    private UIComponentExample ui;

    @Override
    public void onEnable() {
        this.componentManager = new ComponentManager();

        ui = new UIComponentExample(this);

        componentManager.registerComponent(ui);

        Bukkit.getScheduler().runTaskTimer(this, new ComponentRunnable(componentManager), 0L, 2L);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ui.getUi().addViewer(event.getPlayer().getUniqueId());
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }
}
