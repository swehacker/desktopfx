package com.swehacker.hacfx.model;

import java.util.HashSet;
import java.util.Set;

public class Room extends AbstractEntity {
    private String name;
    private Set<Accessory> accessories;

    public Iterable<Accessory> getAccessories() {
        return accessories;
    }

    public void addAccessory(Accessory accessory) {
        if (accessories == null) {
            accessories = new HashSet<>();
        }

        this.accessories.add(accessory);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
