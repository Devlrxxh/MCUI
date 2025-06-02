package dev.lrxh.mCUI;

import dev.lrxh.mCUI.elements.ElementManager;
import dev.lrxh.mCUI.elements.UI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MCUI extends JavaPlugin implements Listener {
    private ElementManager elementManager;
    private UI ui;

    @Override
    public void onEnable() {
        this.elementManager = new ElementManager();

        ui = new UI(this);
        ui.add(new File(this.getDataFolder(), "images/icon.png"));
        ui.add(new File(this.getDataFolder(), "images/icon.png"));

        elementManager.addUI(ui);

        ui.load(8080);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ui.addViewer(event.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskLater(this, () -> {
            ui.removeViewer(event.getPlayer().getUniqueId());
        },  100L); // Update every second
    }
}
