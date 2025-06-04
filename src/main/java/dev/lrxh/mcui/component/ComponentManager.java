package dev.lrxh.mcui.component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComponentManager {
    private final List<UIComponent> components;

    public ComponentManager() {
        this.components = new ArrayList<>();
    }

    public void registerComponent(UIComponent component) {
        components.add(component);
    }

    public void run() {
        for (UIComponent component : components) {
            for (UUID uuid : component.getUi().getViewers()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) return;
                component.tick(player);
            }
        }
    }
}
