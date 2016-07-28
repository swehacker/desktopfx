package com.swehacker.desktopfx.server.legend;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventChangeListener implements MqttCallback {
    private static final Logger LOG = Logger.getLogger(EventChangeListener.class.getName());
    private static final String CLIENT_ID = "desktopfx-server";
    private static final HashMap<String, Event> sensorEvents = new HashMap<>();
    private String serverURI;
    private String subscription;
    private MqttClient client;

    @Autowired
    EventRepository eventRepository;

    public EventChangeListener(String serverURI, String subscription) {
        this.serverURI = serverURI;
        this.subscription = subscription;
    }

    public void start() {
        try {
            client = new MqttClient(serverURI, CLIENT_ID);
            client.connect();
            client.setCallback(this);
            client.subscribe(subscription);
        } catch (MqttException e) {
            LOG.log(Level.SEVERE, "Couldn't connect to MQTT server!", e);
        }
    }

    public void stop() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException mqttException) {
            LOG.severe(mqttException.getMessage());
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        LOG.info(cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Event event = new Event();
        event.setTopic(topic);
        event.setTime(new Date());
        if ( message.toString().equalsIgnoreCase("ON") ) {
            event.setValue(1);
            eventRepository.save(event);
        } else if ( message.toString().equalsIgnoreCase("OFF")) {
            event.setValue(0);
            eventRepository.save(event);
        } else {
            try {
                event.setValue(Double.parseDouble(message.toString()));
                sensorEvents.put(event.getTopic(), event);
            } catch (NumberFormatException nfe) {
                LOG.severe("Invalid command: " + message.toString());
            }
        }
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void scheduledStoreData() {
        for (Event event : sensorEvents.values() ) {
            eventRepository.save(new Event(event));
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOG.info(token.toString());
    }
}
