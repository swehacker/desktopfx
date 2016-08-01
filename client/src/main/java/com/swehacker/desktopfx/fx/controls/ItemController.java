package com.swehacker.desktopfx.fx.controls;

import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public abstract class ItemController extends Region {
    final Text title = new Text("Unknown");

    public void setName(String title) {
        this.title.setText(title);
    }
}
