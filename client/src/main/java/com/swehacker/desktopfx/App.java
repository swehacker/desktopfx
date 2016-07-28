/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Patrik Falk
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

package com.swehacker.desktopfx;

import com.swehacker.desktopfx.configuration.Item;
import com.swehacker.desktopfx.configuration.PropertiesConfiguration;
import com.swehacker.desktopfx.events.EventRepository;
import com.swehacker.desktopfx.openhab.ItemChangedListener;
import com.swehacker.desktopfx.openhab.OpenHABService;
import com.swehacker.desktopfx.screens.ScreenController;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    private static final ConsoleHandler CONSOLE_HANDLER = new ConsoleHandler();
    private static final String OPENHAB_SERVER_ADDRESS =    System.getProperty("openhab.server.address", "localhost");
    private static final String OPENHAB_SERVER_PORT =       System.getProperty("openhab.server.port", "8080");
    private static final String DESKTOPFX_SERVER_ADDRESS =  System.getProperty("desktopfx.server.address", "localhost");
    private static final String DESKTOPFX_SERVER_PORT =     System.getProperty("desktopfx.server.port", "8080");
    private static final String MQTT_SERVER_ADDRESS =       System.getProperty("mqtt.server.address", "tcp://localhost:1883");
    private static final String MQTT_SERVER_TOPIC =         System.getProperty("mqtt.server.topic", "/apartment/#");
    private static List<Item> items;
    private static OpenHABService openHABService;
    private static EventRepository eventRepository;
    private ItemChangedListener listener;
    private ScreenController screenController = new ScreenController();
    private Scene scene;
    private static SimpleObjectProperty<Item> currentItem = new SimpleObjectProperty<>();

    @Override
    public void init() {
        CONSOLE_HANDLER.setLevel(Level.ALL);
        LOG.setLevel(Level.ALL);
        LOG.addHandler(CONSOLE_HANDLER);

        LOG.config("OPENHAB SERVER: " + OPENHAB_SERVER_ADDRESS + ":" + OPENHAB_SERVER_PORT);
        LOG.config("DESKTOPFX SERVER: " + DESKTOPFX_SERVER_ADDRESS + ":" + DESKTOPFX_SERVER_PORT);
        LOG.config("MQTT SERVER: " + MQTT_SERVER_ADDRESS + MQTT_SERVER_TOPIC);

        openHABService = new OpenHABService(OPENHAB_SERVER_ADDRESS, Integer.parseInt(OPENHAB_SERVER_PORT));
        eventRepository = new EventRepository(DESKTOPFX_SERVER_ADDRESS, Integer.parseInt(DESKTOPFX_SERVER_PORT));
        items = new PropertiesConfiguration().getConfig();
        listener = new ItemChangedListener(MQTT_SERVER_ADDRESS, MQTT_SERVER_TOPIC);

        screenController.loadScreens();
        screenController.changeScreen(ScreenController.SCREEN.HOME);
    }

    @Override
    public void stop() throws Exception {
        listener.stop();
        super.stop();
    }

    private void setStylesheets() {
        scene.getStylesheets().setAll("/stylesheets/default.css");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        listener.start();

        // CREATE SCENE
        BorderPane root = new BorderPane();
        root.getStyleClass().addAll("root");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setContent(root);
        root.setTop(screenController);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        scene = new Scene(scrollPane, bounds.getWidth(), bounds.getHeight());
        LOG.config("Setting the Screen to " + bounds.getWidth() + "x" + bounds.getHeight());

        setStylesheets();

        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);

        // START FULL SCREEN
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        LOG.config("Setting the Screen to " + bounds.getWidth() + "x" + bounds.getHeight());

        primaryStage.show();
    }

    /**
     * Gets all the configured items (OpenHAB objects)
     *
     * @return Items in a list.
     */
    public static List<Item> getItems() {
        return items;
    }

    public static OpenHABService getOpenHABService() {
        return openHABService;
    }

    public static EventRepository getEventRepository() {
        return eventRepository;
    }

    public static void setCurrentItem(Item currentItem) {
        App.currentItem.set(currentItem);
    }

    public static Item getCurrentItem() {
        return currentItem.get();
    }

    public static SimpleObjectProperty<Item> currentItemProperty() {
        return currentItem;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App.launch(args);
    }
}
