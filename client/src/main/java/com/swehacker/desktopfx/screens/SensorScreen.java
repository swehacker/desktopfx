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
import com.swehacker.desktopfx.events.EventRepository;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SensorScreen implements Screen {
    private ScreenController parent;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00");
    private List<EventRepository.Event> events;

    @FXML
    LineChart sensor_chart;

    @FXML
    Button backButton;

    public SensorScreen() {
        App.currentItemProperty().addListener(observable -> updateChart());
    }

    public void updateChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Time");
        sensor_chart.getData().clear();
        sensor_chart.setTitle("Sensor monitoring");
        XYChart.Series series = new XYChart.Series();
        series.setName(App.getCurrentItem().getName());
        events = App.getEventRepository().getEvents(App.getCurrentItem().getTopic());
        for (EventRepository.Event event : events) {
            series.getData().add(new XYChart.Data<>(format(event.time), event.value));
        }

        sensor_chart.getData().add(series);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.parent = screenPage;

        backButton.setOnMouseClicked(event -> parent.changeScreen(ScreenController.SCREEN.HOME));
    }

    private String format(Date date) {
        return sdf.format(date);
    }
}
