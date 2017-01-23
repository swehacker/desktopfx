package com.swehacker.hacfx.server;

import java.util.logging.Logger;

public abstract class ServerConfiguration {
    private static final Logger LOG = Logger.getLogger(ServerConfiguration.class.getName());
    private static final String MQTT_SERVER_ADDRESS =       System.getProperty("mqtt.server.address", "tcp://localhost:1883");
    private static final String MQTT_SERVER_TOPIC =         System.getProperty("mqtt.server.topic", "/apartment/#");

    private static final String HACFX_SERVER_ADDRESS =  System.getProperty("hacfx.server.address", "localhost");
    private static final String HACFX_SERVER_PORT =     System.getProperty("hacfx.server.port", "8001");

    public static final DfxService getService() {
        LOG.info("HACFX SERVER: " + HACFX_SERVER_ADDRESS + ":" + HACFX_SERVER_PORT);
        return new DfxService(HACFX_SERVER_ADDRESS, Integer.parseInt(HACFX_SERVER_PORT));
    }

    public static AccessoryChangeListener getAccessoryChangedListener() {
        LOG.info("MQTT SERVER: " + MQTT_SERVER_ADDRESS + MQTT_SERVER_TOPIC);
        return new AccessoryChangeListener(MQTT_SERVER_ADDRESS, MQTT_SERVER_TOPIC);
    }
}
