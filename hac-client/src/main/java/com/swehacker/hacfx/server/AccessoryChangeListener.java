package com.swehacker.hacfx.server;

import com.swehacker.hacfx.App;
import com.swehacker.hacfx.util.NetworkInterfaceUtil;
import javafx.application.Platform;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessoryChangeListener implements MqttCallback {
    private static final Logger LOG = Logger.getLogger(AccessoryChangeListener.class.getName());
    private static final String CLIENT_ID = "hacfx-" + NetworkInterfaceUtil.getFirstMACAddress();
    private String serverURI;
    private String subscription;
    private MqttClient client;

    public AccessoryChangeListener(String serverURI, String subscription) {
        this.serverURI = serverURI;
        this.subscription = subscription;
    }

    public void start() {
        try {
            LOG.info("Connect to the MQTT Server " + serverURI );
            client = new MqttClient(serverURI, CLIENT_ID, new MemoryPersistence());
            client.connect();
            client.setCallback(this);
            client.subscribe(subscription);
        } catch (MqttException e) {
            LOG.log(Level.SEVERE, "Couldn't connect to the MQTT Server!", e);
        }
    }

    public void stop() {
        try {
            LOG.info("Disconnecting from the MQTT Server " + serverURI );
            client.disconnect();
            client.close();
        } catch (MqttException mqttException) {
            LOG.log(Level.INFO, "Couldn't close the client, trying to force!", mqttException);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        LOG.info(cause.getMessage());
        LOG.info("Restarting...");
        stop();
        start();
        LOG.info("Restarted...");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Platform.runLater(() -> {
            App.getMyHome().getAccessories().forEachRemaining(accessory -> {
                if ( topic.equalsIgnoreCase(accessory.getTopic()) ) {
                    accessory.setValue(message.toString());
                    return;
                }
            });
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOG.info(token.toString());
    }
}
