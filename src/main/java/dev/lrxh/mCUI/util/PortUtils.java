package dev.lrxh.mCUI.util;

public class PortUtils {
    private static int port = 8080;

    public static int getPort() {
        return port+=1;
    }
}
