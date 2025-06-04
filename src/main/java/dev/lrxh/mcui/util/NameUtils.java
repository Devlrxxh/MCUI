package dev.lrxh.mcui.util;

public class NameUtils {
    private static int index = 0;

    public static String get() {
        return "mcui-pack-" + index++;
    }
}
