package dev.lrxh.mCUI.util;

import dev.lrxh.mCUI.MCUI;

public class ServerUtils {
    public static int getPort() {
        return MCUI.getInstance().getServer().getPort();
    }
}
