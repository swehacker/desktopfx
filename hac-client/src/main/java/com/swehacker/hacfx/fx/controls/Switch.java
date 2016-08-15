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

package com.swehacker.hacfx.fx.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.logging.Logger;

public class Switch extends ItemController {
    private static final Logger LOG = Logger.getLogger(ItemController.class.toString());
    private boolean on = false;
    private final StringProperty value = new SimpleStringProperty("");
    private final StackPane iconPane = new StackPane();
    private final VBox vBox = new VBox();

    public Switch() {
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().addAll("switch", "switch-off");

        StackPane titlePane = new StackPane();
        titlePane.getStyleClass().add("switch-icon");
        title.getStyleClass().addAll("switch-title");
        titlePane.setAlignment(title, Pos.CENTER);
        titlePane.getChildren().add(title);

        iconPane.getStyleClass().addAll("switch-icon-off");

        vBox.getChildren().addAll(title, iconPane);

        value.addListener((observable)-> {
            if ( value.getValue().equalsIgnoreCase("ON")) {
                turnOn();
            } else if ( value.getValue().equalsIgnoreCase("OFF")) {
                turnOff();
            } else {
                LOG.severe("Unknown command: " + observable.toString());
            }
        });

        this.getChildren().add(vBox);
    }

    public void turnOn() {
        on = true;
        vBox.getStyleClass().add("switch-on");
        vBox.getStyleClass().remove("switch-off");
        iconPane.getStyleClass().add("switch-icon-on");
        iconPane.getStyleClass().remove("switch-icon-off");
    }

    public void turnOff() {
        on = false;
        vBox.getStyleClass().add("switch-off");
        vBox.getStyleClass().remove("switch-on");
        iconPane.getStyleClass().add("switch-icon-off");
        iconPane.getStyleClass().remove("switch-icon-on");
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public boolean isOn() {
        return on;
    }
}
