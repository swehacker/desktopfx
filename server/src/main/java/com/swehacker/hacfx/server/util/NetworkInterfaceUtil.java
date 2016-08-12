package com.swehacker.hacfx.server.util;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Collection of useful tools for handling Network Interfaces
 */
public class NetworkInterfaceUtil {
    private static final Logger LOG = Logger.getLogger(NetworkInterfaceUtil.class.getName());

    /**
     * Get the first MAC address found if one or more network interface configured and is up. Will return null if no
     * interfaces found that is running.
     */
    public static final String getFirstMACAddress() {
        try {
            Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
            NetworkInterface ni;
            while(ifs.hasMoreElements()) {
                ni = ifs.nextElement();
                if ( ni.isUp() && !ni.isLoopback() && !ni.isVirtual() && !ni.isPointToPoint()) {
                    StringBuilder builder = new StringBuilder();
                    byte[] hwa = ni.getHardwareAddress();
                    for (byte num : hwa) {
                        builder.append(String.format("%02x", num));
                        builder.append(":");
                    }
                    builder.deleteCharAt(builder.length() - 1);
                    return builder.toString().toUpperCase();
                }
            }
        } catch (Throwable e) {
            LOG.log(Level.WARNING, "Couldn't identify any network interfaces!", e);
        }

        LOG.fine("Couldn't find a MAC address");
        return null;
    }
}
