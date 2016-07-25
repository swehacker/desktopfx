/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Patrik Falk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.swehacker.desktopfx.tools;

import org.eclipse.paho.client.mqttv3.*;

/**
 * A util class to help debugging MQTT. It will listen on all events and print them in the terminal.
 * Push a switch or wait a few seconds for the temperature sensors to report something.
 */
public class MQTTSubscriber implements MqttCallback {

    private static String CLIENT_ID = "desktop-fx";
    private String serverURI;
    private String subscription;
    private MqttClient client;

    public MQTTSubscriber(String serverURI, String subscription) {
        this.serverURI = serverURI;
        this.subscription = subscription;
    }

    public void run() {
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

    public static void main(String...args) {
        if ( args.length < 2 ) {
            System.out.println("Usage: MQTTSubscriber tcp://<ip>:<port> <subscription>");
            System.out.println("For subscription use /my/room/sensor, /my/# or /my/room/# to receive messages");
            System.exit(0);
        }

        new MQTTSubscriber(args[0], args[1]).run();
    }

    @Override
    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(topic + ": " + message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(token);
    }
}
