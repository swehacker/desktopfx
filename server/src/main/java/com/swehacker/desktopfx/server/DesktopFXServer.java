package com.swehacker.desktopfx.server;

import com.swehacker.desktopfx.server.legend.EventChangeListener;
import com.swehacker.desktopfx.server.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesktopFXServer {
    @Autowired
    EventChangeListener eventChangeListener;

    public static void main(String...args) {
        if ( args.length > 0 ) {
            if (args[0].equalsIgnoreCase("password")) {
                System.out.println(PasswordGenerator.generate());
            }
        }

        SpringApplication.run(DesktopFXServer.class, args);
    }
}
