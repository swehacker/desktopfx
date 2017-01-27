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

package com.swehacker.hacfx;

import com.swehacker.hacfx.configuration.SystemCapabilities;
import com.swehacker.hacfx.fx.screens.ScreenController;
import com.swehacker.hacfx.model.Accessory;
import com.swehacker.hacfx.model.House;
import com.swehacker.hacfx.server.AccessoryChangeListener;
import com.swehacker.hacfx.server.DfxService;
import com.swehacker.hacfx.server.ServerConfiguration;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    private static final ConsoleHandler CONSOLE_HANDLER = new ConsoleHandler();
    private static SimpleObjectProperty<Accessory> currentItem = new SimpleObjectProperty<>();

    private ScreenController screenController = new ScreenController();
    private Scene scene;

    private static AccessoryChangeListener accessoryChangeListener;
    private static DfxService dfxService;
    private static SystemCapabilities systemCapabilities;
    private static House myHome;

    @Override
    public void init() {
        CONSOLE_HANDLER.setLevel(Level.ALL);
        LOG.setLevel(Level.ALL);
        LOG.addHandler(CONSOLE_HANDLER);

        systemCapabilities = new SystemCapabilities();
        dfxService = ServerConfiguration.getService();
        myHome = dfxService.getAccessories();
        accessoryChangeListener = ServerConfiguration.getAccessoryChangedListener();
    }

    @Override
    public void stop() throws Exception {
        accessoryChangeListener.stop();
        super.stop();
    }

    private void setStylesheets() {
        scene.getStylesheets().setAll("/stylesheets/default.css");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        screenController.loadScreens();
        screenController.changeScreen(ScreenController.SCREEN.HOME);

        accessoryChangeListener.start();

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

    public static void setCurrentItem(Accessory currentAccessory) {
        App.currentItem.set(currentAccessory);
    }

    public static Accessory getCurrentItem() {
        return currentItem.get();
    }

    public static SimpleObjectProperty<Accessory> currentItemProperty() {
        return currentItem;
    }

    public static House getMyHome() {
        return myHome;
    }

    public static AccessoryChangeListener getAccessoryChangeListener() {
        return accessoryChangeListener;
    }

    public static DfxService getDxfService() {
        return dfxService;
    }

    public static SystemCapabilities getSystemCapabilities() {
        return systemCapabilities;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App.launch(args);
    }
}
