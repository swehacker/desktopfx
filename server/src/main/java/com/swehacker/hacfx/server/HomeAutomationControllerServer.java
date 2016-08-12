package com.swehacker.hacfx.server;

import com.swehacker.hacfx.server.legend.EventChangeListener;
import com.swehacker.hacfx.server.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeAutomationControllerServer {
    @Autowired
    EventChangeListener eventChangeListener;

    public static void main(String...args) {
        if ( args.length > 0 ) {
            if (args[0].equalsIgnoreCase("password")) {
                System.out.println(PasswordGenerator.generate());
            }
        }

        SpringApplication.run(HomeAutomationControllerServer.class, args);
    }
}
