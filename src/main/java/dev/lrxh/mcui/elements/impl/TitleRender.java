package dev.lrxh.mcui.elements.impl;

import dev.lrxh.mcui.elements.Element;
import dev.lrxh.mcui.elements.UIRender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class TitleRender extends UIRender {
    @Override
    public void render(Player player, Element... elements) {
        player.showTitle(Title.title(Component.empty(), create(elements),
                Title.Times.times(Duration.ofMillis(1), Duration.ofSeconds(1), Duration.ofMillis(1))));
    }
}
