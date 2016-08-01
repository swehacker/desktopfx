package com.swehacker.desktopfx.ha;

import com.swehacker.desktopfx.exceptions.RoomNotFoundException;

import java.util.Iterator;

public interface Home {
    /**
     * Returns the name of your house.
     */
    String getName();

    /**
     * Create a room
     * @param name
     * @return newly created room
     */
    Room createRoom(String name);

    /**
     * Adds a newly created room to the home
     * @param room
     */
    void addRoom(Room room);

    /**
     * Returns a room by it's name;
     * @throws RoomNotFoundException if no room was found, probably a programming error
     */
    Room getRoom(String roomName) throws RoomNotFoundException;

    /**
     * Returns all rooms that belongs to this house.
     */
    Iterator<Room> getRooms();

    /**
     * Returns all accessories in all rooms;
     */
    Iterator<Accessory> getAccessories();
}
