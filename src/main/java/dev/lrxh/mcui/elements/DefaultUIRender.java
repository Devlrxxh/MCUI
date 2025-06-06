package dev.lrxh.mcui.elements;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
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

            player.sendActionBar(builder.build());
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


            player.showTitle(Title.title(Component.empty(), builder.build(),
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


            player.showTitle(Title.title(builder.build(), Component.empty(),
                    Title.Times.times(Duration.ofMillis(1), Duration.ofSeconds(1), Duration.ofMillis(1))));
        }
    };

    public abstract void render(Player player, Object... element);
}
