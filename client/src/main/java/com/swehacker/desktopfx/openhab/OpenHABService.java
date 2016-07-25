/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Patrik Falk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.swehacker.desktopfx.openhab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swehacker.desktopfx.Item;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Properties;

public class OpenHABService {
    private static final Properties labels = new Properties();
    private final Client client;
    private List<Item> items;
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

    public OpenHABService(final String ip, final int port) {
        _restURL = "http://" + ip + ":" + port + "/rest";
        client = ClientBuilder.newBuilder()
                .build();
    }

    public STATE getSwitchState(String item) {
        WebTarget target = client.target(_restURL + "/items/" + item);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if ( response.getStatus() == 200 ) {
            Switch _item = response.readEntity(Switch.class);
            return STATE.convert(_item.state);
        }

        return null;
    }

    public void switchState(String item, STATE state) {
        WebTarget target = client.target(_restURL + "/items/" + item);
        Invocation.Builder builder = target.request(MediaType.TEXT_PLAIN_TYPE);
        builder.post(Entity.entity(state.state, MediaType.TEXT_PLAIN));
    }

    public String getSensorValue(String item) {
        WebTarget target = client.target(_restURL + "/items/" + item);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if ( response.getStatus() == 200 ) {
            SensorValue _item = response.readEntity(SensorValue.class);
            return _item.state;
        }

        return null;
    }

    public static class Switch {
        public String link;
        public String state;
        public String type;
        public String name;
        public String label;
        public List tags;
        public List groupNames;
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
}
