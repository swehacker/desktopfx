package com.swehacker.hacfx.server.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class NetworkInterfaceUtilTest {

    @Test
    public void validateMACAddressLength() {
        Assert.assertEquals(17, NetworkInterfaceUtil.getFirstMACAddress().length());
    }

    @Test
    public void validateMACAddressPattern() {
        Pattern pattern = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
        Assert.assertTrue(pattern.matcher(NetworkInterfaceUtil.getFirstMACAddress()).matches());
    }
}
