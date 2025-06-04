package dev.lrxh.mcui.example;

import dev.lrxh.mcui.component.UIComponent;
import dev.lrxh.mcui.elements.DefaultUIRender;
import dev.lrxh.mcui.elements.Element;
import org.bukkit.entity.Player;

public class UIComponentExample extends UIComponent {
    private final Element element;

    public UIComponentExample() {
        element = register("image_0.png");
        load();
    }

    @Override
    public void tick(Player player) {
        DefaultUIRender.ACTION_BAR.render(player, element);
    }
}
