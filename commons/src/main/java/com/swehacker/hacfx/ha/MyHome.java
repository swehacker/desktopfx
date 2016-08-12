package com.swehacker.hacfx.ha;

import com.swehacker.hacfx.ha.exceptions.RoomNotFoundException;

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
    public void addAccessories(List<Accessory> newAccessories) {
        for ( Accessory accessory : newAccessories ) {
            addAccessory(accessory);
        }
    }

    @Override
    public void addAccessories(Iterator<Accessory> newAccessories) {
        newAccessories.forEachRemaining(accessory -> addAccessory(accessory));
    }

    @Override
    public void addAccessory(Accessory accessory) {
        String roomName = accessory.getRoom();
        Room room;
        try {
            room = getRoom(roomName);
        } catch (RoomNotFoundException rnfe) {
            room = createRoom(roomName);
        }

        room.addAccessory(accessory);
        addRoom(room);
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
