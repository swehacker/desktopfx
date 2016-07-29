package com.swehacker.desktopfx.server.legend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/event")
public class EventRestEndPoint {
    @Autowired
    EventRepository eventRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Event> getAllEventsByTopic(@RequestParam("topic") String topic) {
        System.out.println(topic);
        return eventRepository.findByTopic(topic);
    }
}
