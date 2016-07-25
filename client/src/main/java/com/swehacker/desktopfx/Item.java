package com.swehacker.desktopfx;

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
}