package com.swehacker.desktopfx.server.legend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/event")
public class EventRestEndPoint {
    @Autowired
    EventRepository eventRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Event> getAllEventsByTopic(@RequestParam("topic") String topic) {
        System.out.println(topic);
        return eventRepository.findByTopic(topic);
    }
}
