package com.swehacker.desktopfx.ha;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Accessory {
    public enum Type {
        SWITCH,
        LAMP,
        TEMPERATURE,
        HUMIDITY
    }

    private String name;
    private String label;
    private String topic;
    private String room;
    private Type type;
    private StringProperty value = new SimpleStringProperty();

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

    public Type getType() {
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

    public void setTypeByString(String type) {
        checkIfEmpty(type);
        if (type.equalsIgnoreCase("Switch")) {
            this.type = Type.SWITCH;
        } else if (type.equalsIgnoreCase("Lamp") ) {
            this.type = Type.LAMP;
        } else if (type.equalsIgnoreCase("Temperature")) {
            this.type = Type.TEMPERATURE;
        } else if (type.equalsIgnoreCase("Humidity")) {
            this.type = Type.HUMIDITY;
        } else {
            throw new IllegalArgumentException("Configuration of type is wrong");
        }
    }

    public void setType(Type type) {
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
