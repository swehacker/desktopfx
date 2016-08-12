# Home Automation Controller

## Introduction
Home Automation frontend for desktop, embedded (raspberry pi), Android and iOS for OpenHAB 2.

I wanted a better experience than the website, using different clients to be able to get sensor data, switch on/off accessories. I also wanted to control my accessories with my voice.
The client is built using Java FX and is communicating with OpenHAB 2 through MQTT and REST.

The application consist of a server, MQTT server and a client.

### MQTT
To be able to get instant sensor data to the client I had to install a MQTT server and configure OpenHAB to send changes of states to the MQTT server.

### Server
A server is used to handle accesssories (OpenHAB items like Switches/Sensors). Instead of integrating directly to OpenHAB all clients communicate through the service which should make it easy to extend the Home Automation system later on.
The server will have a configuration file for all the accessories and the client get it's configuration from the server so we don't have to distribute configure files to the clients.

### Client
The client is a JavaFX application that can run on your desktop, Raspberry Pi, iOS or Android devices.
