package dev.lrxh.mcui.component;

import dev.lrxh.mcui.elements.Element;
import dev.lrxh.mcui.elements.UI;
import dev.lrxh.mcui.pack.AssetsLoader;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public abstract class UIComponent {
    private final UI ui;

    public UIComponent() {
        this.ui = new UI();
    }

    public UI getUi() {
        return ui;
    }

    public abstract void tick(Player player);

    public Element register(File file, int height, int ascent) {
        return getUi().register(file, height, ascent);
    }

    public Element register(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return getUi().register(AssetsLoader.load(url));
        }

        return getUi().register(url);
    }

    public Element register(String url, int height, int ascent) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return getUi().register(AssetsLoader.load(url), height, ascent);
        }

        return getUi().register(url, height, ascent);
    }

    public void addViewer(Player player) {
        addViewer(player.getUniqueId());
    }

    public void removeViewer(Player player) {
        removeViewer(player.getUniqueId());
    }

    public void removeViewer(UUID uuid) {
        getUi().removeViewer(uuid);
    }

    public void addViewer(UUID uuid) {
        getUi().addViewer(uuid);
    }

    public void load() {
        getUi().load();
    }
}
