package com.swehacker.desktopfx.openhab;

import java.util.logging.Logger;

public abstract class OpenHabServiceConfiguration {
    private static final Logger LOG = Logger.getLogger(OpenHabServiceConfiguration.class.getName());
    private static final String OPENHAB_SERVER_ADDRESS =    System.getProperty("openhab.server.address", "localhost");
    private static final String OPENHAB_SERVER_PORT =       System.getProperty("openhab.server.port", "8080");

    public static final OpenHABService getOpenHABService() {
        LOG.info("OPENHAB SERVER: " + OPENHAB_SERVER_ADDRESS + ":" + OPENHAB_SERVER_PORT);
        return new OpenHABService(OPENHAB_SERVER_ADDRESS, Integer.parseInt(OPENHAB_SERVER_PORT));
    }
}
