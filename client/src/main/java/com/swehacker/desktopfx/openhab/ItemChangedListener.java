package com.swehacker.desktopfx.openhab;

import com.swehacker.desktopfx.App;
import com.swehacker.desktopfx.util.NetworkUtil;
import com.swehacker.desktopfx.configuration.Item;
import javafx.application.Platform;
import org.eclipse.paho.client.mqttv3.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemChangedListener implements MqttCallback {
    private static final Logger LOG = Logger.getLogger(ItemChangedListener.class.getName());
    private static final String CLIENT_ID = "desktopfx-" + NetworkUtil.getMACAddress();
    private String serverURI;
    private String subscription;
    private MqttClient client;

    public ItemChangedListener(String serverURI, String subscription) {
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
            LOG.log(Level.SEVERE, "Couldn't connect to the MQTT Server!", e);
        }
    }

    public void stop() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException mqttException) {
            LOG.log(Level.INFO, "Couldn't close the client, trying to force!", mqttException);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        LOG.info(cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Platform.runLater(() -> {
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
