package dev.lrxh.mcui.elements;

import net.kyori.adventure.text.Component;

import java.io.File;

public class Element {
    private final int unicode;
    private final File image;
    private final int height;
    private final int ascent;
    private final Component component;
    private ElementSpace elementSpace;

    public Element(int unicode, File image, int height, int ascent) {
        this.unicode = unicode;
        this.image = image;
        this.height = height;
        this.ascent = ascent;
        this.elementSpace = null;
        this.component = Component.text(get());
    }

    public Element(String string) {
        this.unicode = 0;
        this.image = null;
        this.height = 0;
        this.ascent = 0;
        this.elementSpace = null;
        this.component = Component.text(string);
    }

    public Element(Component component) {
        this.unicode = 0;
        this.image = null;
        this.height = 0;
        this.ascent = 0;
        this.elementSpace = null;
        this.component = component;
    }

    public void setElementSpace(ElementSpace elementSpace) {
        this.elementSpace = elementSpace;
    }

    public int getUnicode() {
        return unicode;
    }

    public File getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getAscent() {
        return ascent;
    }

    public Component getComponent() {
        return component;
    }

    private String get() {
        if (elementSpace == null) {
            return String.valueOf((char) unicode);
        }

        return elementSpace.getChar() + (char) unicode;
    }

}

