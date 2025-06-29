package dev.lrxh.mcui.elements;

import dev.lrxh.mcui.elements.impl.ActionBarRender;
import dev.lrxh.mcui.elements.impl.TitleRender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.ShadowColor;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class UIRender {
    public final static UIRender ACTION_BAR_RENDER = new ActionBarRender();
    public final static UIRender TITLE_RENDER = new TitleRender();

    public Component create(Element... elements) {
        TextComponent.Builder builder = Component.text();

        for (Element element : elements) {
            builder.append(element.getComponent());
        }

        return builder.shadowColor(ShadowColor.shadowColor(255, 255, 255, 0))
                .build();
    }

    public void render(Player player, List<Element> elements) {
        render(player, elements.toArray(new Element[0]));
    }

    public abstract void render(Player player, Element... elements);
}
