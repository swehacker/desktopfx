package com.swehacker.desktopfx.ha;

import com.swehacker.desktopfx.exceptions.RoomNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MyHome implements Home {
    private String name;
    private HashSet<Room> rooms = new HashSet<>();

    @Override
    public String getName() {
        return name;
    }

    public Room createRoom(String roomName) {
        return new Room(roomName);
    }

    @Override
    public Room getRoom(String roomName) {
        for ( Room room : rooms ) {
            if ( room.getName().equalsIgnoreCase(roomName) ) {
                return room;
            }
        }

        throw new RoomNotFoundException("Could not find the room");
    }

    @Override
    public Iterator<Room> getRooms() {
        return rooms.iterator();
    }

    @Override
    public void addRoom(Room newRoom) {
        rooms.add(newRoom);
    }

    @Override
    public Iterator<Accessory> getAccessories() {
        List<Accessory> accessories = new ArrayList<>();
        for ( Room room : rooms ) {
            room.getAccessories().forEachRemaining(accessory -> accessories.add(accessory));
        }

        return accessories.iterator();
    }
}
