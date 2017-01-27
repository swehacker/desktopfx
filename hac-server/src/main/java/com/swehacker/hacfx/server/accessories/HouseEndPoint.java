package com.swehacker.hacfx.server.accessories;

import com.swehacker.hacfx.server.model.Accessory;
import com.swehacker.hacfx.server.model.House;
import com.swehacker.hacfx.server.model.Room;
import com.swehacker.hacfx.server.openhab.OpenHABService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "houses", description = "Accessory Management")
@RestController
@RequestMapping("/api/house")
public class HouseEndPoint {
    @Autowired
    private OpenHABService openHABService;

    @Autowired
    HouseRepository houseRepository;

    @ApiOperation(value = "findAll", nickname = "FindAll", notes = "Returns a list of houses")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<House> getAllAccessories() {
        return houseRepository.findAll();
    }

    @ApiOperation(value = "countAll", nickname = "countAll", notes = "Returns number of houses")
    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = "application/json")
    public Long countAll() {
        return houseRepository.count();
    }

    @ApiOperation(value = "findById", nickname = "FindById", notes = "Returns a specific house")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public House findAccessory(@PathVariable("id") Long id) {
        return houseRepository.findOne(id);
    }

    @ApiOperation(value = "findRoomsByHouseId", nickname = "FindRoomsByHouseId", notes = "Returns rooms that is configured in a house")
    @RequestMapping(value = "/{houseId}/rooms", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Room> findRoomsByHouseId(@PathVariable("houseId") Long houseId) {
        return houseRepository.findOne(houseId).rooms;
    }

    @ApiOperation(value = "countRoomsByHouseId", nickname = "countRoomsByHouseId", notes = "Returns number of rooms that is configured in a house")
    @RequestMapping(value = "/{houseId}/rooms/count", method = RequestMethod.GET, produces = "application/json")
    public int countRoomsByHouseId(@PathVariable("houseId") Long houseId) {
        return houseRepository.findOne(houseId).rooms.size();
    }

    @ApiOperation(value = "findAccessoriesInARoomByHouseId", nickname = "findAccessoriesInARoomByHouseId", notes = "Returns accessories in a room")
    @RequestMapping(value = "/{houseId}/rooms/{roomId}", method = RequestMethod.GET, produces = "application/json")
    public Room findOneRoomByHouseIdAndRoomId(@PathVariable("houseId") Long houseId, @PathVariable("roomId") Long roomId) {
        return houseRepository.findRoom(roomId);
    }

    @ApiOperation(value = "findAccessoriesInARoomByHouseId", nickname = "findAccessoriesInARoomByHouseId", notes = "Returns accessories in a room")
    @RequestMapping(value = "/{houseId}/rooms/{roomId}/accessories", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Accessory> findAccessoriesInARoomByHouseId(@PathVariable("houseId") Long houseId, @PathVariable("roomId") Long roomId) {
        return houseRepository.findRoom(roomId).getAccessories();
    }

    @ApiOperation(value = "switch", nickname = "switch", notes = "Change the state of a accessory, like turning a lamp on or off")
    @RequestMapping(value = "/{id}/switch/{state}", method = RequestMethod.PUT)
    public void changeState(@PathVariable String id, @PathVariable String state) {
        openHABService.switchState(id, OpenHABService.STATE.convert(state.toUpperCase()));
    }
}
