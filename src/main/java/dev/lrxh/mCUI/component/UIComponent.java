package dev.lrxh.mCUI.component;

import dev.lrxh.mCUI.elements.UI;
import org.bukkit.entity.Player;

public abstract class UIComponent {
    public UI getUi() {
        return ui;
    }

    private final UI ui;

    public UIComponent() {
        this.ui = new UI();
    }

    public abstract void tick(Player player);
}
