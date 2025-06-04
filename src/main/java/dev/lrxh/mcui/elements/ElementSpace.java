package dev.lrxh.mcui.elements;

public enum ElementSpace {
    BACKSPACE_1(0x0101),
    BACKSPACE_2(0x0102),
    BACKSPACE_3(0x0103),
    BACKSPACE_4(0x0104),
    BACKSPACE_5(0x0105),
    BACKSPACE_6(0x0106),
    BACKSPACE_7(0x0107),
    BACKSPACE_8(0x0108),
    BACKSPACE_9(0x0109),
    BACKSPACE_10(0x010A),
    BACKSPACE_11(0x010B),
    BACKSPACE_12(0x010C),
    BACKSPACE_13(0x010D),
    BACKSPACE_14(0x010E),
    BACKSPACE_15(0x010F),
    BACKSPACE_16(0x0110),
    BACKSPACE_17(0x0111),
    BACKSPACE_32(0x0120),
    BACKSPACE_33(0x0121),
    BACKSPACE_48(0x0130),
    BACKSPACE_49(0x0131),
    BACKSPACE_64(0x0140),
    BACKSPACE_65(0x0141),

    FORWARDSPACE_1(0x0201),
    FORWARDSPACE_2(0x0202),
    FORWARDSPACE_3(0x0203),
    FORWARDSPACE_4(0x0204),
    FORWARDSPACE_5(0x0205),
    FORWARDSPACE_6(0x0206),
    FORWARDSPACE_7(0x0207),
    FORWARDSPACE_8(0x0208),
    FORWARDSPACE_9(0x0209),
    FORWARDSPACE_10(0x020A),
    FORWARDSPACE_11(0x020B),
    FORWARDSPACE_12(0x020C),
    FORWARDSPACE_13(0x020D),
    FORWARDSPACE_14(0x020E),
    FORWARDSPACE_15(0x020F),
    FORWARDSPACE_16(0x0210),
    FORWARDSPACE_32(0x0220),
    FORWARDSPACE_48(0x0230),
    FORWARDSPACE_64(0x0240);

    private final int unicode;

    ElementSpace(int unicode) {
        this.unicode = unicode;
    }

    public String getChar() {
        return String.valueOf((char) unicode);
    }

    public Element createElement() {
        Element element = new Element(unicode, null, 9, 0);
        element.setElementSpace(this);
        return element;
    }
}
