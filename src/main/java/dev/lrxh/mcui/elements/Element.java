package dev.lrxh.mcui.elements;

import java.io.File;

public class Element {
    private final int unicode;
    private final File image;
    private final int height;
    private final int ascent;
    private ElementSpace elementSpace;

    public Element(int unicode, File image, int height, int ascent) {
        this.unicode = unicode;
        this.image = image;
        this.height = height;
        this.ascent = ascent;
        this.elementSpace = null;
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

    public String get() {
        if (elementSpace == null) {
            return String.valueOf((char) unicode);
        }

        return elementSpace.getChar() + (char) unicode;
    }

}

