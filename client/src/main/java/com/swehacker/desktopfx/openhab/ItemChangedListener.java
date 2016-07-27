package com.swehacker.desktopfx.openhab;

import com.swehacker.desktopfx.App;
import com.swehacker.desktopfx.configuration.Item;
import com.swehacker.desktopfx.controls.Humidity;
import com.swehacker.desktopfx.controls.ItemController;
import com.swehacker.desktopfx.controls.Switch;
import com.swehacker.desktopfx.controls.Temperature;
import com.swehacker.desktopfx.screens.HomeScreen;
import javafx.application.Platform;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Date;
import java.util.logging.Logger;

public class ItemChangedListener implements MqttCallback {
    private static final Logger LOG = Logger.getLogger(ItemChangedListener.class.getName());
    private static final String CLIENT_ID = "desktop-fx" + new Date().toString().hashCode();
    private String serverURI;
    private String subscription;
    private MqttClient client;

    public ItemChangedListener(String serverURI, String subscription) {
        this.serverURI = serverURI;
        this.subscription = subscription;
    }

    public void start() {
        try {
            System.out.println("Connecting to " + serverURI);
            client = new MqttClient(serverURI, CLIENT_ID);
            client.connect();
            client.setCallback(this);
            client.subscribe(subscription);
        } catch (MqttException e) {
            e.printStackTrace();
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
        Platform.runLater(() -> {
            LOG.info(topic + ": " + message.toString());
            for (Item item : App.getItems()) {
                if (topic.equalsIgnoreCase(item.getTopic())) {
                    item.setValue(message.toString());
                }
            }
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOG.info(token.toString());
    }
}
