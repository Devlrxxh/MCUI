package dev.lrxh.mCUI.elements;

import java.util.ArrayList;
import java.util.List;

public class ElementManager {
    private final List<UI> uis;

    public ElementManager() {
        this.uis = new ArrayList<>();
    }

    public void addUI(UI ui) {
        this.uis.add(ui);
    }
}
