package com.swehacker.desktopfx.configuration;

public class SystemCapabilities {
    private final String OS_NAME = System.getProperty("ensemble.os.name", System.getProperty("os.name"));
    private final String OS_ARCH = System.getProperty("ensemble.os.arch", System.getProperty("os.arch"));
    private final boolean IS_IOS = "iOS".equals(OS_NAME) || "iOS Simulator".equals(OS_NAME);
    private final boolean IS_ANDROID = "android".equals(System.getProperty("javafx.platform")) || "Dalvik".equals(System.getProperty("java.vm.name"));
    private final boolean IS_EMBEDDED = "arm".equals(OS_ARCH) && !IS_IOS && !IS_ANDROID;
    private final boolean IS_DESKTOP = !IS_EMBEDDED && !IS_IOS && !IS_ANDROID;

    public boolean isAndroid() {
        return IS_ANDROID;
    }

    public boolean isIos() {
        return IS_IOS;
    }

    public boolean isEmbedded() {
        return IS_EMBEDDED;
    }

    public boolean isDesktop() {
        return IS_DESKTOP;
    }
}
