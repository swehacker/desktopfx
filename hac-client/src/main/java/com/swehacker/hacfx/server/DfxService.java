package com.swehacker.hacfx.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swehacker.hacfx.model.House;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class DfxService {
    private static final Logger LOG = Logger.getLogger(DfxService.class.getName());
    private final Client client;
    private String _restURL;

    public enum STATE {
        ON("ON"),
        OFF("OFF"),
        UNKNOWN("UNKNOWN");

        private final String state;
        STATE(final String state) {
            this.state = state;
        }

        public static STATE convert(String state) {
            for (STATE s: STATE.values()) {
                if ( s.name().equalsIgnoreCase(state) ) {
                    return s;
                }
            }

            return UNKNOWN;
        }
    }

    public DfxService(final String ip, final int port) {
        LOG.info("Connecting to the DfxServer " + ip + ":" + port );
        _restURL = "http://" + ip + ":" + port + "/api";
        client = ClientBuilder.newBuilder()
                .build();
    }

    //TODO: Hardcoded value, client should be able to configure wich house it will display
    public House getAccessories() {
        WebTarget target = client.target(_restURL + "/house/1");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if ( response.getStatus() == 200 ) {
            House house = response.readEntity(new GenericType<House>() {});
            return house;
        }

        return null;
    }

    public void switchState(String item, STATE state) {
        WebTarget target = client.target(_restURL + "/accessories/" + item + "/switch/" + state.toString());
        Form form = new Form();
        form.param("state", state.toString());
        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if ( response.getStatus() != 200 ) {
            LOG.severe(response.toString());
        }
    }

    public List<Event> getEvents(String topic) {
        try {
            WebTarget target = client.target(_restURL + "/events?topic=" + URLEncoder.encode(topic, "UTF-8"));
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

    public static class SensorValue {
        public String link;
        public String state;
        @JsonIgnore
        public List stateDescription;
        public String type;
        public String name;
        public String label;
        public String category;
        public List tags;
        public List groupNames;
    }

    public static class SwitchValue {
        public String link;
        public String state;
        public String type;
        public String name;
        public String label;
        @JsonIgnore
        public List tags;
        @JsonIgnore
        public List groupNames;
    }
}
