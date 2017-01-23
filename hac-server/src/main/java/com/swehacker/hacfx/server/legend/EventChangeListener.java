package com.swehacker.hacfx.server.legend;

import com.swehacker.hacfx.server.util.NetworkInterfaceUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EventChangeListener implements MqttCallback, ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOG = Logger.getLogger(EventChangeListener.class.getName());
    private static final String MQTT_SERVER_ADDRESS = System.getProperty("mqtt.server.address", "tcp://localhost:1883");
    private static final String MQTT_SERVER_TOPIC = System.getProperty("mqtt.server.topic", "/#");
    private static final String CLIENT_ID = "hacfx-server-" + NetworkInterfaceUtil.getFirstMACAddress();
    private static final HashMap<String, Event> sensorEvents = new HashMap<>();
    private String serverURI;
    private String subscription;
    private MqttClient client;

    @Autowired
    EventRepository eventRepository;

    public EventChangeListener() {
        this.serverURI = MQTT_SERVER_ADDRESS;
        this.subscription = MQTT_SERVER_TOPIC;
    }

    public void start() {
        try {
            client = new MqttClient(serverURI, CLIENT_ID, new MemoryPersistence());
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
        event.setTime(new Date().getTime());
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

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOG.info("Starting Event Change Listener");
        start();
    }
}
