package dev.lrxh.mCUI.elements;

import org.bukkit.entity.Player;

public enum DefaultUIRender {
        ACTION_BAR {
        @Override
        public void render(Player player, Element element) {
            char unicodeChar = (char) element.getUnicode();
            player.sendActionBar(String.valueOf(unicodeChar));
        }
    };

    public abstract void render(Player player, Element element);
}
