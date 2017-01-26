package com.swehacker.hacfx.server.legend;

import com.swehacker.hacfx.server.model.Event;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(value = "events", description = "Accessory Event Management")
@RestController
@RequestMapping(value = "/api/events")
public class EventRestEndPoint {
    private static long FOURTEEN_DAYS = 1000 * 60 * 24 * 14;

    @Autowired
    EventRepository eventRepository;

    @ApiOperation(value = "findAll", nickname = "findAll", notes = "Returns all events (by default 14 days back)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Event> getAllByTopic(
            @ApiParam(name = "topic", value = "Get all events for a specific accessory (MQTT Topic Name)") @RequestParam(name = "topic", required = false) String topic,
            @ApiParam(name = "until", value = "A specific date in ms, default is two weeks") @RequestParam(name = "until", required = false) Long until) {
        if (until == null) {
            until = new Date().getTime() - FOURTEEN_DAYS;
        }

        if (topic == null || topic.isEmpty()) {
            return eventRepository.findAll(until);
        } else {
            return eventRepository.findByTopic(topic, until);
        }
    }
}

