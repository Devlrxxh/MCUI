package dev.lrxh.mCUI.component;

import dev.lrxh.mCUI.elements.UI;
import org.bukkit.entity.Player;

public abstract class UIComponent {
    private final UI ui;

    public UIComponent() {
        this.ui = new UI();
    }

    public UI getUi() {
        return ui;
    }

    public abstract void tick(Player player);
}
