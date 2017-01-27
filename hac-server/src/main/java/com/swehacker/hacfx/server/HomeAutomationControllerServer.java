package com.swehacker.hacfx.server;

import com.swehacker.hacfx.server.events.EventChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeAutomationControllerServer {
    @Autowired
    EventChangeListener eventChangeListener;

    public static void main(String...args) {
        SpringApplication.run(HomeAutomationControllerServer.class, args);
    }
}
