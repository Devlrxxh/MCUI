package dev.lrxh.mcui;

import dev.lrxh.mcui.component.ComponentManager;
import dev.lrxh.mcui.component.ComponentRunnable;
import dev.lrxh.mcui.pack.PackServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCUI extends JavaPlugin {
    public static MCUI INSTANCE;
    private ComponentManager componentManager;
    private PackServer packServer;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.componentManager = new ComponentManager();

        packServer = new PackServer();
        packServer.start();

        Bukkit.getScheduler().runTaskTimer(this, new ComponentRunnable(componentManager), 0L, 2L);
    }


    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public PackServer getPackServer() {
        return packServer;
    }
}
