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

import com.swehacker.desktopfx.configuration.PropertiesConfiguration;
import com.swehacker.desktopfx.openhab.OpenHABService;
import com.swehacker.desktopfx.screens.ScreenController;
import javafx.application.Application;
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
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static final ConsoleHandler CONSOLE_HANDLER = new ConsoleHandler();
    private static final String SERVER_ADDRESS = "192.168.1.5";
    private static final int SERVER_PORT = 8080;
    private static List<Item> items;
    private static OpenHABService openHABService = new OpenHABService(SERVER_ADDRESS, SERVER_PORT);

    private ScreenController screenController = new ScreenController();
    private Scene scene;

    @Override
    public void init() {
        items = new PropertiesConfiguration().getConfig();
        CONSOLE_HANDLER.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(CONSOLE_HANDLER);

        screenController.loadScreens();
        screenController.changeScreen(ScreenController.SCREEN.HOME);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void setStylesheets() {
        scene.getStylesheets().setAll("/stylesheets/default.css");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(openHABService.getSwitchState("Living_FloorLamp"));
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
        logger.config("Setting the Screen to " + bounds.getWidth() + "x" + bounds.getHeight());

        setStylesheets();

        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);

        // START FULL SCREEN
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        logger.config("Setting the Screen to " + bounds.getWidth() + "x" + bounds.getHeight());

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App.launch(args);
    }
}
