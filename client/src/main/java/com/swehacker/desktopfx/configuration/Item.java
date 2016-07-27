package com.swehacker.desktopfx.configuration;

import javafx.beans.property.StringProperty;

public interface Item {
    enum ItemType {
        SWITCH,
        TEMPERATURE,
        HUMIDITY
    }

    public String getTopic();
    public String getLabel();
    public String getName();
    public String getRoom();
    public ItemType getType();
    public String getValue();
    public StringProperty valueProperty();
    public void setValue(String value);
}
