package com.swehacker.desktopfx.server.history;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class EventRepository {
    private static final Logger LOG = Logger.getLogger(EventRepository.class.getName());
    private final String _restURL;
    private final Client client;

    public EventRepository(final String ip, final int port) {
        _restURL = "http://" + ip + ":" + port;
        client = ClientBuilder.newBuilder().build();
    }

    public List<Event> getEvents(String topic) {
        try {
            WebTarget target = client.target(_restURL + "/event?topic=" + URLEncoder.encode(topic, "UTF-8"));
            Response response = target.request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<List<Event>>() {});
            }
        } catch (UnsupportedEncodingException uee) {
            LOG.warning("Couldn't encode the URL");
        }

        return null;
    }

    public static class Event {
        public Date time;
        public String topic;
        public double value;
    }
}
