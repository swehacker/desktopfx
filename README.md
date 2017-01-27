# Home Automation Controller

## Introduction
Home Automation frontend for OpenHAB2, where the client can run on a desktop, embedded (raspberry pi), Android phone and iOS.

I wanted a better experience than the website, using different clients to be able to get sensor data, switch on/off accessories. I also wanted to control my accessories with my voice.
The clients is communicating with OpenHAB 2 via a server, and MQTT for handling event. The Client talks to the server via REST.

The client is built using Java FX and because I wanted to run the client on a Raspberry Pi using Framebuffer (JavaFX) and on old Ipads/Android tablets that I have laying around.

The application consist of a server, MQTT server and a client.

### MQTT
OpenHAB is configured to send all states/values via MQTT server, so we can trigger events and store data outside of OpenHAB.

### Server
A server handles all accesssories (OpenHAB items like Switches/Sensors). Instead of integrating directly to OpenHAB, all clients communicate through this service. It also makes it easy to integrate to another system to future proof the clients.
The server handles all the clients configuration so we don't have to distribute configuration using files to the clients.

### Client
The client is a JavaFX application that can run on your desktop, Raspberry Pi, iOS or Android devices.

## Documentation

### REST api documentation
When the server is started, the documentation can be found here
```
http://<ip>:<port>/swagger-ui.html
```

## Features
* Centralized configuration of the clients
* Simple and clean layout
* Client can run on Android and Raspberry Pi with touch screen.
* Probably will work on iOS, initial test went well.
* Basic configuration (House/Rooms/Accessory)
* Stores all events for historic data, when a lamp was turned on or temperature for a specific period.
* Display historic data for sensors

## Backlog (not in a specific order)
* Dockerize everything for easy deployment
* ReactNative client for mobile devices and web
* Advanced configuration
  * Set up more than one house and switch between them easily
  * Create screens with groups, not just all or a specific room
  * Internationalize the client, like being able to display fahrenheit instead of Celsius.
* Make the client/server more fault tolerant
* Secure the server and client
* Automatically update clients when a new version has been released
* Display the highest and lowest temperature and humidity during the day.
* Display if the temperature/humidity is going up or down.
* Have different ui when running on different screen sizes and devices.
