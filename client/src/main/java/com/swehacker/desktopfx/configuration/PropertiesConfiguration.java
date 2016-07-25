/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Patrik Falk
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

package com.swehacker.desktopfx.configuration;

import com.swehacker.desktopfx.tools.MQTTSubscriber;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;

public class PropertiesConfiguration {
    private static final String FILENAME = "config.properties";
    private void watchFile() {
        URL url = MQTTSubscriber.class.getClassLoader().getResource(FILENAME);
        String fullPath = url.getPath();

        Path path = FileSystems.getDefault().getPath(fullPath.substring(0, fullPath.indexOf(FILENAME)) + "/");
        System.out.println("Watching: " + path.toString());
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                final WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changed = (Path) event.context();
                    System.out.println(changed);
                    if (changed.endsWith(FILENAME)) {
                        System.out.println("My file has changed");
                    }
                }
                // reset the key
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregistered");
                }
            }
        } catch (IOException | InterruptedException exception ) {
            exception.printStackTrace();
        }
    }
    private void readConfig() {
        Properties prop = new Properties();
        HashSet<String> keys = new HashSet<>();

        try {
            InputStream is = MQTTSubscriber.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(is);

            Enumeration e = prop.propertyNames();
            while( e.hasMoreElements() ) {
                String tmp = e.nextElement().toString();
                keys.add(tmp.substring(0, tmp.indexOf('.')));
            }

            for (String key: keys) {
                System.out.println(key);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String...args) {
        new PropertiesConfiguration().watchFile();
    }
}
