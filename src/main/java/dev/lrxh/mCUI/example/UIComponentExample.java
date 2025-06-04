package dev.lrxh.mCUI.example;

import dev.lrxh.mCUI.component.UIComponent;
import dev.lrxh.mCUI.elements.DefaultUIRender;
import dev.lrxh.mCUI.elements.Element;
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
