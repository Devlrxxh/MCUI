package dev.lrxh.mcui;

import dev.lrxh.mcui.component.ComponentManager;
import dev.lrxh.mcui.component.ComponentRunnable;
import dev.lrxh.mcui.example.UIComponentExample;
import dev.lrxh.mcui.pack.PackServer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCUI extends JavaPlugin implements Listener {
    public static MCUI INSTANCE;
    private ComponentManager componentManager;
    private UIComponentExample ui;
    private PackServer packServer;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.componentManager = new ComponentManager();

        packServer = new PackServer();
        packServer.start();


        // Example UI Component
        MCUI.INSTANCE.getComponentManager().registerComponent(new UIComponentExample());
        //


        Bukkit.getScheduler().runTaskTimer(this, new ComponentRunnable(componentManager), 0L, 2L);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ui.addViewer(event.getPlayer().getUniqueId());
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public PackServer getPackServer() {
        return packServer;
    }
}
