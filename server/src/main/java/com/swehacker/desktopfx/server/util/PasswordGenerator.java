package com.swehacker.desktopfx.server.util;

import java.util.Random;

public class PasswordGenerator {
    private final static String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!#&*+-@/()=?<>";

    public static String generateHex() {
        byte[] passwordBytes = new byte[20];
        new Random().nextBytes(passwordBytes);
        StringBuilder sb = new StringBuilder();
        for ( byte b : passwordBytes ) {
            sb.append(String.format("%x", b));
        }

        return sb.toString();
    }

    public static String generate() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            sb.append(CHARACTERS.charAt(r.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }
}
