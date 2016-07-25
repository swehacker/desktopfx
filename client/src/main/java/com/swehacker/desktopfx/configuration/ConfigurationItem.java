package com.swehacker.desktopfx.configuration;

import com.swehacker.desktopfx.Item;

public class ConfigurationItem implements Item {
    private String name;
    private String label;
    private String topic;
    private String room;
    private ItemType type;

    ConfigurationItem() {

    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRoom() {
        return room;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    void setName(String name) {
        checkIfEmpty(name);
        this.name = name;
    }

    void setTopic(String topic) {
        checkIfEmpty(topic);
        this.topic = topic;
    }

    void setRoom(String room) {
        checkIfEmpty(room);
        this.room = room;
    }

    void setType(String type) {
        checkIfEmpty(type);
        if (type.equalsIgnoreCase("Switch")) {
            this.type = ItemType.SWITCH;
        } else if (type.equalsIgnoreCase("Temperature")) {
            this.type = ItemType.TEMPERATURE;
        } else if (type.equalsIgnoreCase("Humidity")) {
            this.type = ItemType.HUMIDITY;
        } else {
            throw new IllegalArgumentException("Configuration of type is wrong");
        }
    }

    void setType(ItemType type) {
        this.type = type;
    }

    private void checkIfEmpty(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("Configuration error, a property is empty");
    }
}
