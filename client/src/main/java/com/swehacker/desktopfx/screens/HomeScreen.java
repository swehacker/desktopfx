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

package com.swehacker.desktopfx.screens;

import com.swehacker.desktopfx.App;
import com.swehacker.desktopfx.configuration.Item;
import com.swehacker.desktopfx.controls.Humidity;
import com.swehacker.desktopfx.controls.ItemController;
import com.swehacker.desktopfx.controls.Switch;
import com.swehacker.desktopfx.controls.Temperature;
import com.swehacker.desktopfx.openhab.OpenHABService;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen implements Screen {
    private ScreenController parent;
    public static List<ItemController> controllers = new ArrayList<>();

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
        update();
    }

    private void update() {
        try {
            for (Item item : App.getItems()) {
                if (item.getType() == Item.ItemType.SWITCH) {
                    Switch roomSwitch = new Switch();
                    roomSwitch.setName(item.getName());
                    roomSwitch.setMQTTPath(item.getTopic());
                    if (OpenHABService.STATE.ON == App.getOpenHABService().getSwitchState(item.getLabel())) {
                        roomSwitch.turnOn();
                    }

                    roomSwitch.setOnMouseClicked(event -> {
                        roomSwitch.flip();
                        if (roomSwitch.isOn()) {
                            App.getOpenHABService().switchState(item.getLabel(), OpenHABService.STATE.ON);
                        } else {
                            App.getOpenHABService().switchState(item.getLabel(), OpenHABService.STATE.OFF);
                        }
                    });
                    switchPanel.getChildren().add(roomSwitch);
                    controllers.add(roomSwitch);
                } else if (item.getType() == Item.ItemType.TEMPERATURE) {
                    Temperature roomTemperature = new Temperature();
                    roomTemperature.setName(item.getName());
                    roomTemperature.setMQTTPath(item.getTopic());
                    roomTemperature.setValue(App.getOpenHABService().getSensorValue(item.getLabel()));
                    roomTemperature.setOnMouseClicked(event -> {
                        parent.changeScreen(ScreenController.SCREEN.SENSOR);
                    });
                    sensorPanel.getChildren().add(roomTemperature);
                    controllers.add(roomTemperature);
                } else if (item.getType() == Item.ItemType.HUMIDITY) {
                    Humidity humidity = new Humidity();
                    humidity.setName(item.getName());
                    humidity.setMQTTPath(item.getTopic());
                    humidity.setValue(App.getOpenHABService().getSensorValue(item.getLabel()));
                    humidity.setOnMouseClicked(event -> {
                        parent.changeScreen(ScreenController.SCREEN.SENSOR);
                    });
                    sensorPanel.getChildren().add(humidity);
                    controllers.add(humidity);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
