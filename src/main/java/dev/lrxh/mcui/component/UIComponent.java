package dev.lrxh.mcui.component;

import dev.lrxh.mcui.elements.Element;
import dev.lrxh.mcui.elements.UI;
import dev.lrxh.mcui.pack.AssetsLoader;
import org.bukkit.entity.Player;

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

    public Element register(String image, int height, int ascent) {
        return getUi().register(image, height, ascent);
    }

    public Element register(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return getUi().register(AssetsLoader.load(url));
        }

        return getUi().register(url);
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
