package com.swehacker.hacfx.server.accessories;

import com.swehacker.hacfx.server.model.Accessory;
import com.swehacker.hacfx.server.openhab.OpenHABService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "accessories", description = "Accessory Management")
@RestController
@RequestMapping("/api/accessories")
public class AccessoryEndPoint {
    @Autowired
    private OpenHABService openHABService;

    @Autowired
    AccessoryRepository accessoryRepository;

    @ApiOperation(value = "findAll", nickname = "FindAll", notes = "Returns a list of configured accessories")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Accessory> getAllAccessories() {
        return accessoryRepository.findAll();
    }

    @ApiOperation(value = "findById", nickname = "FindById", notes = "Returns an accessory")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Accessory findAccessory(@PathVariable("id") Long id) {
        return accessoryRepository.findOne(id);
    }

    @ApiOperation(value = "switch", nickname = "switch", notes = "Change the state of a accessory, like turning a lamp on or off")
    @RequestMapping(value = "/{id}/switch/{state}", method = RequestMethod.PUT)
    public void changeState(@PathVariable String id, @RequestParam String state) {
        openHABService.switchState(id, OpenHABService.STATE.convert(state.toUpperCase()));
    }

    @ApiOperation(value = "getSwitchState", nickname = "getSwitchState", notes = "Return state of a switch or a lamp")
    @RequestMapping(value = "/{id}/switch", method = RequestMethod.GET)
    public String getState(@PathVariable String id) {
        return openHABService.getSwitchState(id).name();
    }

    @ApiOperation(value = "getSensorValue", nickname = "getSensorValue", notes = "Return the current value of a sensor")
    @RequestMapping(value = "/{id}/sensor", method = RequestMethod.GET)
    public String getSensorValue(@PathVariable String id) {
        return openHABService.getSensorValue(id);
    }
}
