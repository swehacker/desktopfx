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

package com.swehacker.hacfx.fx.screens;

import com.swehacker.hacfx.App;
import com.swehacker.hacfx.fx.controls.Humidity;
import com.swehacker.hacfx.fx.controls.Lamp;
import com.swehacker.hacfx.fx.controls.Switch;
import com.swehacker.hacfx.fx.controls.Temperature;
import com.swehacker.hacfx.model.Accessory;
import com.swehacker.hacfx.model.Room;
import com.swehacker.hacfx.server.DfxService;
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
            for ( Room room: App.getMyHome().getRooms()) {
                for ( Accessory accessory : room.getAccessories() ) {
                    accessories.add(accessory);
                }
            }

            for (Accessory accessory : accessories ) {
                if (accessory.getType() == Accessory.Type.SWITCH) {
                    Switch roomSwitch = new Switch();
                    roomSwitch.setName(accessory.getLabel());
                    roomSwitch.setValue(App.getDxfService().switchState(accessory.getOpenHABId()).name());
                    roomSwitch.setOnMouseClicked(event -> {
                        if (roomSwitch.isOn()) {
                            App.getDxfService().switchState(accessory.getOpenHABId(), DfxService.STATE.OFF);
                        } else {
                            App.getDxfService().switchState(accessory.getOpenHABId(), DfxService.STATE.ON);
                        }
                    });
                    roomSwitch.valueProperty().bind(accessory.valueProperty());
                    switchPanel.getChildren().add(roomSwitch);
                } else if (accessory.getType() == Accessory.Type.LAMP) {
                    Lamp lampSwitch = new Lamp();
                    lampSwitch.setName(accessory.getLabel());
                    lampSwitch.setValue(App.getDxfService().switchState(accessory.getOpenHABId()).name());
                    lampSwitch.setOnMouseClicked(event -> {
                        if (lampSwitch.isOn()) {
                            App.getDxfService().switchState(accessory.getOpenHABId(), DfxService.STATE.OFF);
                        } else {
                            App.getDxfService().switchState(accessory.getOpenHABId(), DfxService.STATE.ON);
                        }
                    });
                    lampSwitch.valueProperty().bind(accessory.valueProperty());
                    switchPanel.getChildren().add(lampSwitch);
                } else if (accessory.getType() == Accessory.Type.TEMPERATURE) {
                    Temperature roomTemperature = new Temperature();
                    roomTemperature.setName(accessory.getLabel());
                    accessory.setValue(App.getDxfService().sensorValue(accessory.getOpenHABId()).toString());
                    roomTemperature.valueProperty().bind(accessory.valueProperty());
                    roomTemperature.setOnMouseClicked(event -> {
                        parent.changeScreen(ScreenController.SCREEN.SENSOR);
                        App.setCurrentItem(accessory);
                    });
                    sensorPanel.getChildren().add(roomTemperature);
                } else if (accessory.getType() == Accessory.Type.HUMIDITY) {
                    Humidity humidity = new Humidity();
                    humidity.setName(accessory.getLabel());
                    accessory.setValue(App.getDxfService().sensorValue(accessory.getOpenHABId()).toString());
                    humidity.valueProperty().bind(accessory.valueProperty());
                    humidity.setOnMouseClicked(event -> {
                        parent.changeScreen(ScreenController.SCREEN.SENSOR);
                        App.setCurrentItem(accessory);
                    });
                    sensorPanel.getChildren().add(humidity);
                }
            }
        } catch (Throwable t) {
            LOG.log(Level.SEVERE, "Couldn't create items!", t);
        }
    }
}
