package com.swehacker.desktopfx.server;

import java.util.logging.Logger;

public abstract class ServerConfiguration {
    private static final Logger LOG = Logger.getLogger(ServerConfiguration.class.getName());
    private static final String MQTT_SERVER_ADDRESS =       System.getProperty("mqtt.server.address", "tcp://localhost:1883");
    private static final String MQTT_SERVER_TOPIC =         System.getProperty("mqtt.server.topic", "/apartment/#");

    private static final String DESKTOPFX_SERVER_ADDRESS =  System.getProperty("desktopfx.server.address", "localhost");
    private static final String DESKTOPFX_SERVER_PORT =     System.getProperty("desktopfx.server.port", "8080");

    public static final DfxService getService() {
        LOG.info("DESKTOPFX SERVER: " + DESKTOPFX_SERVER_ADDRESS + ":" + DESKTOPFX_SERVER_PORT);
        return new DfxService(DESKTOPFX_SERVER_ADDRESS, Integer.parseInt(DESKTOPFX_SERVER_PORT));
    }

    public static AccessoryChangeListener getAccessoryChangedListener() {
        LOG.info("MQTT SERVER: " + MQTT_SERVER_ADDRESS + MQTT_SERVER_TOPIC);
        return new AccessoryChangeListener(MQTT_SERVER_ADDRESS, MQTT_SERVER_TOPIC);
    }
}
