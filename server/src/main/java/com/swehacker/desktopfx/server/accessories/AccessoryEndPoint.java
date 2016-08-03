package com.swehacker.desktopfx.server.accessories;

import com.swehacker.desktopfx.ha.Accessory;
import com.swehacker.desktopfx.server.openhab.OpenHABService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
@RequestMapping("/rest/accessory")
public class AccessoryEndPoint {
    @Autowired
    private OpenHABService openHABService;

    @Autowired
    AccessoryConfiguration accessoryConfiguration;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterator<Accessory> getHome() {
        return accessoryConfiguration.getHome().getAccessories();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST, consumes = "application/json")
    public void setValue(@PathVariable String name, @RequestParam String state) {
        openHABService.switchState(name, OpenHABService.STATE.convert(state));
    }
}
