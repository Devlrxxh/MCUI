package dev.lrxh.mcui.util;

import dev.lrxh.mcui.MCUI;

public class ServerUtils {
    public static int getPort() {
        return MCUI.getInstance().getServer().getPort();
    }
}
