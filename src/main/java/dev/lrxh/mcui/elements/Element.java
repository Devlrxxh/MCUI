package dev.lrxh.mcui.elements;

import java.io.File;

public class Element {
    private final int unicode;
    private final File image;
    private final int height;
    private final int ascent;

    public Element(int unicode, File image, int height, int ascent) {
        this.unicode = unicode;
        this.image = image;
        this.height = height;
        this.ascent = ascent;
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
}

