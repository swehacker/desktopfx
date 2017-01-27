package com.swehacker.hacfx.model;

import java.util.Set;

public class House extends AbstractEntity {
    private String name;
    private String location;
    public Set<Room> rooms;

    public Iterable<Room> getRooms() {
        return rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Accessory findByTopic(String topic) {
        for ( Room room : getRooms() ) {
            for ( Accessory accessory: room.getAccessories() ) {
                if ( topic.equalsIgnoreCase(accessory.getTopic()) ) {
                    return accessory;
                }
            }
        }

        return null;
    }
}
