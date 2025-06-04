package dev.lrxh.mcui.elements;

import org.bukkit.entity.Player;

public enum DefaultUIRender {
    ACTION_BAR {
        @Override
        public void render(Player player, Element... element) {
            StringBuilder builder = new StringBuilder();

            for (Element e : element) {
                builder.append(e.get());
            }

            player.sendActionBar(builder.toString());
        }
    };

    public abstract void render(Player player, Element... element);
}
