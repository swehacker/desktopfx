package com.swehacker.desktopfx.ha;

import com.swehacker.desktopfx.ha.exceptions.RoomNotFoundException;

import java.util.Iterator;
import java.util.List;

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
     * Add an accessory to the configured room
     *
     * @param accessory
     */
    void addAccessory(Accessory accessory);

    /**
     * Add new accessories which will add every accessory to specific room
     * @param newAccessories
     */
    void addAccessories(List<Accessory> newAccessories);

    /**
     * Add new accessories which will add every accessory to specific room
     * @param newAccessories
     */
    void addAccessories(Iterator<Accessory> newAccessories);

    /**
     * Returns all accessories in all rooms;
     */
    Iterator<Accessory> getAccessories();
}
