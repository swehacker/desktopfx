package com.swehacker.desktopfx.controls;

import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public abstract class ItemController extends Region {
    final Text title = new Text("Unknown");
    private String mqtt_path;

    public void setName(String title) {
        this.title.setText(title);
    }

    public void setMQTTPath(String path) {
        this.mqtt_path = path;
    }

    public String getMQTTPath() {
        return mqtt_path;
    }
}
