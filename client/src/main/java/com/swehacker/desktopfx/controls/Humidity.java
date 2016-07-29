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

package com.swehacker.desktopfx.controls;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This represents a humidity sensor.
 */
public class Humidity extends ItemController {
    private final Text value = new Text("- -");
    private final Text percent = new Text("%");

    public Humidity() {
        getStyleClass().addAll("sensor");

        VBox vBox = new VBox();
        StackPane sensorTop = new StackPane();
        sensorTop.getStyleClass().add("sensor-top");
        title.getStyleClass().add("sensor-top");
        sensorTop.getChildren().add(title);
        sensorTop.setAlignment(title, Pos.CENTER);
        vBox.getChildren().add(sensorTop);


        StackPane sensorCenter = new StackPane();
        value.getStyleClass().add("sensor-center");
        sensorCenter.getStyleClass().add("sensor-center");
        sensorCenter.getChildren().add(value);
        sensorCenter.setAlignment(value, Pos.CENTER);
        vBox.getChildren().add(sensorCenter);

        StackPane sensorBottom = new StackPane();
        percent.getStyleClass().add("sensor-bottom");
        sensorBottom.getStyleClass().add("sensor-bottom");
        sensorBottom.getChildren().add(percent);
        sensorBottom.setAlignment(percent, Pos.BOTTOM_RIGHT);
        vBox.getChildren().add(sensorBottom);

        this.getChildren().add(vBox);
    }

    public StringProperty valueProperty() {
        return value.textProperty();
    }

    public void setValue(String text) {
        try {
            value.setText("" + Double.parseDouble(text));
        } catch (Throwable t) {
            System.out.println("Ej initierad sensor, hoppar över");
        }
    }
}
