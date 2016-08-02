package com.swehacker.desktopfx.events;

import com.swehacker.desktopfx.openhab.AccessoryChangedListener;

import java.util.logging.Logger;

public abstract class EventRepositoryConfiguration {
    private static final Logger LOG = Logger.getLogger(EventRepositoryConfiguration.class.getName());
    private static final String MQTT_SERVER_ADDRESS =       System.getProperty("mqtt.server.address", "tcp://localhost:1883");
    private static final String MQTT_SERVER_TOPIC =         System.getProperty("mqtt.server.topic", "/apartment/#");

    private static final String DESKTOPFX_SERVER_ADDRESS =  System.getProperty("desktopfx.server.address", "localhost");
    private static final String DESKTOPFX_SERVER_PORT =     System.getProperty("desktopfx.server.port", "8080");

    public static final EventRepository getEventRepository() {
        LOG.info("DESKTOPFX SERVER: " + DESKTOPFX_SERVER_ADDRESS + ":" + DESKTOPFX_SERVER_PORT);
        return new EventRepository(DESKTOPFX_SERVER_ADDRESS, Integer.parseInt(DESKTOPFX_SERVER_PORT));
    }

    public static AccessoryChangedListener getAccessoryChangedListener() {
        LOG.info("MQTT SERVER: " + MQTT_SERVER_ADDRESS + MQTT_SERVER_TOPIC);
        return new AccessoryChangedListener(MQTT_SERVER_ADDRESS, MQTT_SERVER_TOPIC);
    }
}
