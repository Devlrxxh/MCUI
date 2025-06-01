package dev.lrxh.mCUI.elements;

import java.io.File;

public class Element {
    private final int unicode;
    private final File image;

    public Element(int unicode, File image) {
        this.unicode = unicode;
        this.image = image;
    }

    public int getUnicode() {
        return unicode;
    }

    public File getImage() {
        return image;
    }
}

