package com.swehacker.desktopfx.server;

import com.swehacker.desktopfx.server.legend.EventChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesktopFXServer {
    @Autowired
    EventChangeListener eventChangeListener;

    public static void main(String...args) {
        SpringApplication.run(DesktopFXServer.class, args);
    }
}
