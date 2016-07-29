package com.swehacker.desktopfx.server;

import com.swehacker.desktopfx.server.legend.EventChangeListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DesktopFXServer {
    private static final String MQTT_SERVER_ADDRESS = System.getProperty("mqtt.server.address", "tcp://localhost:1883");
    private static final String MQTT_SERVER_TOPIC = System.getProperty("mqtt.server.topic", "/#");

    public static void main(String...args) {
        SpringApplication.run(DesktopFXServer.class, args);
    }

    @Bean
    public EventChangeListener getEventChangeListener() {
        EventChangeListener eventChangeListener = new EventChangeListener(MQTT_SERVER_ADDRESS, MQTT_SERVER_TOPIC);
        eventChangeListener.start();
        return eventChangeListener;
    }
}
