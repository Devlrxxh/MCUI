package dev.lrxh.mcui.elements;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

public enum DefaultUIRender {
    ACTION_BAR {
        @Override
        public void render(Player player, Object... objects) {
            TextComponent.Builder builder = Component.text();

            for (Object object : objects) {
                if (object instanceof Element element) {
                    builder.append(Component.text(element.get()));
                } else if (object instanceof String string) {
                    builder.append(Component.text(string));
                } else if (object instanceof Component component) {
                    builder.append(component);
                } else if (object instanceof ElementSpace elementSpace) {
                    builder.append(Component.text(elementSpace.createElement().get()));
                }
            }

            player.sendActionBar(builder.build());
        }
    };

    public abstract void render(Player player, Object... element);
}
