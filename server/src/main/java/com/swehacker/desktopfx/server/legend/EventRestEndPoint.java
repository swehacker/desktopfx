package com.swehacker.desktopfx.server.legend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "events", description = "Accessory Event Management")
@RestController
@RequestMapping(value = "/rest/event")
public class EventRestEndPoint {
    @Autowired
    EventRepository eventRepository;

    @ApiOperation(value = "getAllEventsByTopic", nickname = "getAllEventsByTopic", notes = "Returns a list of events for a specific accessory - MQTT topic name")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Event> getAllEventsByTopic(@RequestParam("topic") String topic) {
        return eventRepository.findByTopic(topic);
    }
}
