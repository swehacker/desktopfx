package com.swehacker.desktopfx.ha;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Accessory {
    private String name;
    private String label;
    private String topic;
    private String room;
    private ItemType type;
    private StringProperty value = new SimpleStringProperty();

    public enum ItemType {
        SWITCH,
        LAMP,
        TEMPERATURE,
        HUMIDITY
    }

    public Accessory() { }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public ItemType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        checkIfEmpty(name);
        this.name = name;
    }

    public void setTopic(String topic) {
        checkIfEmpty(topic);
        this.topic = topic;
    }

    public void setRoom(String room) {
        checkIfEmpty(room);
        this.room = room;
    }

    public void setType(String type) {
        checkIfEmpty(type);
        if (type.equalsIgnoreCase("Switch")) {
            this.type = ItemType.SWITCH;
        } else if (type.equalsIgnoreCase("Lamp") ) {
            this.type = ItemType.LAMP;
        } else if (type.equalsIgnoreCase("Temperature")) {
            this.type = ItemType.TEMPERATURE;
        } else if (type.equalsIgnoreCase("Humidity")) {
            this.type = ItemType.HUMIDITY;
        } else {
            throw new IllegalArgumentException("Configuration of type is wrong");
        }
    }

    public void setType(ItemType type) {
        this.type = type;
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

    private void checkIfEmpty(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("Configuration error, a property is empty");
    }
}
