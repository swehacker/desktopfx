package com.swehacker.desktopfx.configuration;

import com.swehacker.desktopfx.App;
import com.swehacker.desktopfx.exceptions.RoomNotFoundException;
import com.swehacker.desktopfx.ha.Accessory;
import com.swehacker.desktopfx.ha.Home;
import com.swehacker.desktopfx.ha.MyHome;
import com.swehacker.desktopfx.ha.Room;

import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

public abstract class AccessoryConfiguration {
    private static final Logger LOG = Logger.getLogger(AccessoryConfiguration.class.getName());

    public static Home getHome() {
        MyHome home = new MyHome();

        for ( Accessory accessory : getConfig() ) {
            String roomName = accessory.getRoom();
            Room room;
            try {
                room = home.getRoom(roomName);
            } catch (RoomNotFoundException rnfe) {
                LOG.info("Adding new room " + roomName);
                room = home.createRoom(roomName);
            }

            room.addAccessory(accessory);
            home.addRoom(room);
        }

        return home;
    }

    private static List<Accessory> getConfig() {
        Properties prop = new Properties();
        ArrayList<Accessory> accessories = new ArrayList<>();

        readConfig(prop, accessories);
        group(accessories);

        return accessories;
    }

    private static void readConfig(Properties prop, List<Accessory> accessories) {
        HashSet<String> keys = new HashSet<>();
        try {
            InputStreamReader is = new InputStreamReader(AccessoryConfiguration.class.getClassLoader().getResourceAsStream("config.properties"), "UTF-8");
            prop.load(is);

            Enumeration e = prop.propertyNames();
            while( e.hasMoreElements() ) {
                String tmp = e.nextElement().toString();
                keys.add(tmp.substring(0, tmp.indexOf('.')));
            }

            for (String key: keys) {
                accessories.add(get(prop, key));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Accessory get(Properties prop, String key) {
        Accessory item = new Accessory();
        item.setTopic(prop.getProperty(key + ".topic"));
        item.setLabel(prop.getProperty(key + ".label"));
        item.setName(prop.getProperty(key + ".name"));
        item.setRoom(prop.getProperty(key + ".group"));
        item.setType(prop.getProperty(key + ".type"));
        try {
            if (item.getType() == Accessory.ItemType.SWITCH) {
                item.setValue(App.getOpenHABService().getSwitchState(item.getLabel()).name());
            } else {
                item.setValue(App.getOpenHABService().getSensorValue(item.getLabel()));
            }
        } catch (Throwable t) {
            item.setValue("");
            LOG.warning("Couldn't retrieve current status of " + item.getName());
        }

        LOG.info(item.getTopic() + ": " + item.getValue());


        return item;
    }

    private static void group(List items) {
        Collections.sort(items, new Comparator<Accessory>() {
            @Override
            public int compare(Accessory accessory1, Accessory accessory2)
            {
                return accessory1.getRoom().compareTo(accessory2.getRoom());
            }
        });
    }
}
