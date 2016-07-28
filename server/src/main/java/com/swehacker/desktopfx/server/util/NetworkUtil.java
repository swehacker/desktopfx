package com.swehacker.desktopfx.server.util;

import java.net.NetworkInterface;
import java.util.Date;
import java.util.logging.Logger;

public class NetworkUtil {
    private static final Logger LOG = Logger.getLogger(NetworkUtil.class.getName());

    public static final String getMACAddress() {
        try {
            return new String(NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress());
        } catch (Exception e) {
            LOG.severe("Couldn't find the MAC address");
        }

        return Long.toString(new Date().getTime());
    }
}
