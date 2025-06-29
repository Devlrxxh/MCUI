package dev.lrxh.mcui.elements.impl;

import dev.lrxh.mcui.elements.Element;
import dev.lrxh.mcui.elements.UIRender;
import org.bukkit.entity.Player;

public class ActionBarRender extends UIRender {
    @Override
    public void render(Player player, Element... elements) {
        player.sendActionBar(create(elements));
    }
}
