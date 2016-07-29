package com.swehacker.desktopfx.server.util;

import java.net.NetworkInterface;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Create a simple client id unique to the machine based on the MAC address. The client doesn't seem to work correctly
 * sometimes if there are several clients with the same client id. Used MAC address so it is easy to see which machine
 * the client is running from.
 *
 * It will fallback to current time if MAC address couldn't be found/used.
 */
public class ClientIdUtil {
    private static final Logger LOG = Logger.getLogger(ClientIdUtil.class.getName());

    public static final String getId() {
        try {
            return new String(NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress());
        } catch (Exception e) {
            LOG.fine("Couldn't find the MAC address, using current time instead");
        }

        return Long.toString(new Date().getTime());
    }
}
