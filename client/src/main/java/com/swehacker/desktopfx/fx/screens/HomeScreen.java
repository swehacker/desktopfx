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

package com.swehacker.desktopfx.fx.screens;

import com.swehacker.desktopfx.App;
import com.swehacker.desktopfx.ha.Accessory;
import com.swehacker.desktopfx.fx.controls.Humidity;
import com.swehacker.desktopfx.fx.controls.Lamp;
import com.swehacker.desktopfx.fx.controls.Switch;
import com.swehacker.desktopfx.fx.controls.Temperature;
import com.swehacker.desktopfx.openhab.OpenHABService;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeScreen implements Screen {
    private static Logger LOG = Logger.getLogger(HomeScreen.class.getName());
    private ScreenController parent;

    @FXML
    BorderPane mainPane;

    @FXML
    HBox headerPane;

    @FXML
    FlowPane switchPanel;

    @FXML
    FlowPane sensorPanel;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.parent = screenPage;
        init();
    }

    public void init() {
        switchPanel.prefWidthProperty().bind(this.parent.widthProperty().subtract(260));
        update();
    }

    private void update() {
        try {
            ArrayList<Accessory> accessories = new ArrayList<>();
            System.out.println(App.getOpenHABService().getSwitchState("WANHAO_4S"));
            App.getMyHome().getAccessories().forEachRemaining(accessories::add);

            for (Accessory accessory : accessories ) {
                if (accessory.getType() == Accessory.ItemType.SWITCH) {
                    Switch roomSwitch = new Switch();
                    roomSwitch.setName(accessory.getName());

                    roomSwitch.setOnMouseClicked(event -> {
                        if (roomSwitch.isOn()) {
                            App.getOpenHABService().switchState(accessory.getLabel(), OpenHABService.STATE.OFF);
                        } else {
                            App.getOpenHABService().switchState(accessory.getLabel(), OpenHABService.STATE.ON);
                        }
                    });
                    roomSwitch.valueProperty().bind(accessory.valueProperty());
                    switchPanel.getChildren().add(roomSwitch);
                } else if (accessory.getType() == Accessory.ItemType.LAMP) {
                    Lamp lampSwitch = new Lamp();
                    lampSwitch.setName(accessory.getName());

                    lampSwitch.setOnMouseClicked(event -> {
                        if (lampSwitch.isOn()) {
                            App.getOpenHABService().switchState(accessory.getLabel(), OpenHABService.STATE.OFF);
                        } else {
                            App.getOpenHABService().switchState(accessory.getLabel(), OpenHABService.STATE.ON);
                        }
                    });
                    lampSwitch.valueProperty().bind(accessory.valueProperty());
                    switchPanel.getChildren().add(lampSwitch);
                } else if (accessory.getType() == Accessory.ItemType.TEMPERATURE) {
                    Temperature roomTemperature = new Temperature();
                    roomTemperature.setName(accessory.getName());
                    roomTemperature.valueProperty().bind(accessory.valueProperty());
                    roomTemperature.setOnMouseClicked(event -> {
                        parent.changeScreen(ScreenController.SCREEN.SENSOR);
                        App.setCurrentItem(accessory);
                    });
                    accessory.setValue(App.getOpenHABService().getSensorValue(accessory.getLabel()));
                    sensorPanel.getChildren().add(roomTemperature);
                } else if (accessory.getType() == Accessory.ItemType.HUMIDITY) {
                    Humidity humidity = new Humidity();
                    humidity.setName(accessory.getName());
                    humidity.valueProperty().bind(accessory.valueProperty());
                    humidity.setOnMouseClicked(event -> {
                        parent.changeScreen(ScreenController.SCREEN.SENSOR);
                        App.setCurrentItem(accessory);
                    });
                    accessory.setValue(App.getOpenHABService().getSensorValue(accessory.getLabel()));
                    sensorPanel.getChildren().add(humidity);
                }
            }
        } catch (Throwable t) {
            LOG.log(Level.SEVERE, "Couldn't create items!", t);
        }
    }
}
