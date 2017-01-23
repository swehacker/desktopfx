package com.swehacker.hacfx.server.accessories;

import com.swehacker.hacfx.ha.Accessory;
import com.swehacker.hacfx.server.openhab.OpenHABService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@Api(value = "accessories", description = "Accessory Management")
@RestController
@RequestMapping("/api/accessories")
public class AccessoryEndPoint {
    @Autowired
    private OpenHABService openHABService;

    @Autowired
    AccessoryConfiguration accessoryConfiguration;

    @ApiOperation(value = "findAll", nickname = "FindAll", notes = "Returns a list of configured accessories")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterator<Accessory> getAllAccessories() {
        return accessoryConfiguration.getHome().getAccessories();
    }

    @ApiOperation(value = "changeState", nickname = "changeState", notes = "Change the state of a accessory, like turning an accessory on or off")
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public void changeState(@PathVariable String name, @RequestParam String state) {
        openHABService.switchState(name, OpenHABService.STATE.convert(state));
    }
}
