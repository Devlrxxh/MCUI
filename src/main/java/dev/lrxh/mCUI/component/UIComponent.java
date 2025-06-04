package dev.lrxh.mCUI.component;

import dev.lrxh.mCUI.elements.Element;
import dev.lrxh.mCUI.elements.UI;
import dev.lrxh.mCUI.pack.AssetsLoader;
import org.bukkit.entity.Player;

import java.io.File;

public abstract class UIComponent {
    private final UI ui;

    public UIComponent() {
        this.ui = new UI();
    }

    public UI getUi() {
        return ui;
    }

    public abstract void tick(Player player);

    public Element register(File file) {
        return getUi().register(file);
    }

    public Element register(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return getUi().register(AssetsLoader.load(url));
        }

        return getUi().register(url);
    }

    public void load() {
        getUi().load();
    }
}
