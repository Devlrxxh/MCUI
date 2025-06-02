package dev.lrxh.mCUI.util;

public class NameUtils {
    private static int index = 0;

    public static String get() {
        return "mcui-pack-" + index++;
    }
}
