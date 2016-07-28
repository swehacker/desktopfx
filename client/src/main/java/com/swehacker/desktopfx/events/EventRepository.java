package com.swehacker.desktopfx.events;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class EventRepository {
    private final String _restURL;
    private final Client client;

    public EventRepository(final String ip, final int port) {
        _restURL = "http://" + ip + ":" + port;
        client = ClientBuilder.newBuilder().build();
    }

    public List<Event> getEvents(String topic) {
        WebTarget target = client.target(_restURL + "/event/?topic=" + URLEncoder.encode(topic));
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if ( response.getStatus() == 200 ) {
            return response.readEntity(new GenericType<List<Event>>() {});
        }

        return null;
    }

    public static class Event {
        public Date time;
        public String topic;
        public double value;
    }
}
