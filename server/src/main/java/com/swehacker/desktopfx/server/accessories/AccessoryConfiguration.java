package com.swehacker.desktopfx.server.accessories;

import com.swehacker.desktopfx.ha.Accessory;
import com.swehacker.desktopfx.ha.Home;
import com.swehacker.desktopfx.ha.MyHome;
import com.swehacker.desktopfx.server.openhab.OpenHABService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

@Service
public class AccessoryConfiguration {
    private static final Logger LOG = Logger.getLogger(AccessoryConfiguration.class.getName());

    @Autowired
    private OpenHABService openHABService;

    public Home getHome() {
        MyHome home = new MyHome();
        home.addAccessories(getConfig());
        return home;
    }

    private List<Accessory> getConfig() {
        Properties prop = new Properties();
        ArrayList<Accessory> accessories = new ArrayList<>();

        readConfig(prop, accessories);
        group(accessories);

        return accessories;
    }

    private void readConfig(Properties prop, List<Accessory> accessories) {
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

    private Accessory get(Properties prop, String key) {
        Accessory item = new Accessory();
        item.setTopic(prop.getProperty(key + ".topic"));
        item.setLabel(prop.getProperty(key + ".label"));
        item.setName(prop.getProperty(key + ".name"));
        item.setRoom(prop.getProperty(key + ".group"));
        item.setTypeByString(prop.getProperty(key + ".type"));
        try {
            if (item.getType() == Accessory.Type.SWITCH) {
                item.setValue(openHABService.getSwitchState(item.getLabel()).name());
            } else {
                item.setValue(openHABService.getSensorValue(item.getLabel()));
            }
        } catch (Throwable t) {
            item.setValue("");
            LOG.warning("Couldn't retrieve current status of " + item.getName());
        }

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
