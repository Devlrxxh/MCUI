package dev.lrxh.mcui.elements;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.ARGBLike;
import org.bukkit.entity.Player;

import java.time.Duration;

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

            Component component = builder.shadowColor(ShadowColor.shadowColor(255, 255, 255, 0))
                    .build();

            player.sendActionBar(component);
        }
    },
    TITLE_BOTTOM {
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
            Component component = builder.shadowColor(ShadowColor.shadowColor(255, 255, 255, 0))
                    .build();

            player.showTitle(Title.title(Component.empty(), component,
                    Title.Times.times(Duration.ofMillis(1), Duration.ofSeconds(1), Duration.ofMillis(1))));
        }
    },
    TITLE_TOP {
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

            Component component = builder.shadowColor(ShadowColor.shadowColor(255, 255, 255, 0))
                    .build();

            player.showTitle(Title.title(component, Component.empty(),
                    Title.Times.times(Duration.ofMillis(1), Duration.ofSeconds(1), Duration.ofMillis(1))));
        }
    };

    public abstract void render(Player player, Object... element);
}
