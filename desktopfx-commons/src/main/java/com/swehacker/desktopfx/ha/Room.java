package com.swehacker.desktopfx.ha;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Room {
    private final String name;
    private final List<Accessory> accessories = new ArrayList<>();

    Room(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Iterator<Accessory> getAccessories() {
        return accessories.listIterator();
    }

    public void addAccessory(Accessory newAccessory) {
        accessories.add(newAccessory);
    }
}
