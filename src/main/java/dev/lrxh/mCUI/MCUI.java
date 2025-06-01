package dev.lrxh.mCUI;

import dev.lrxh.mCUI.elements.ElementManager;
import dev.lrxh.mCUI.elements.UI;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MCUI extends JavaPlugin {
    private ElementManager elementManager;

    @Override
    public void onEnable() {
        this.elementManager = new ElementManager();

        UI ui = new UI();
        ui.add(new File(this.getDataFolder(), "images/icon.png"));

        elementManager.addUI(ui);

        try {
            ui.generate(new File(this.getDataFolder(), "output"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        elementManager.addUI(new UI());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
