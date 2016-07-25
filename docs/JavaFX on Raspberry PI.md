# Install JavaFX on Raspberry Pi
Since Java 1.8.60 Oracle dropped JavaFX on ARM platform and released it to the community, this means that we need to install javafx ontop of java. We need also to add some more memory for javafx to allow fx to run smoothly (it requires a lot of memory).

The good news is that we don't need X or any DM to run javafx clients, we can do it directly from the frame buffer.

## Configure the Pi for more memory
The older versions Raspberry Pi Raspian preallocate a fixed amount of the system memory for use by the video engine (VRAM). The utility raspi_config can be used to alter how much memory is allocated to VRAM.
Newer versions of Raspian can dynamically allocate system memory for use as VRAM.
The minimum recommended memory split for JFX on the Pi is 128 MBytes, with many applications requiring 256 MBytes.
This JavaFX texture caching mechanism currently defaults to 512 MBytes - a value that will likely exceed what is available on the Pi. Given the limited amount of VRAM on the Pi, it is quite possible that an image intensive application might fail when the cache exceeds the available system limit.


### Raspbian Jessy
Download the openjfx file from @chriswhocodes (http://chriswhocodes.com/) and unpack it into the JDK1.8 catalog (usually /usr/lib/jvm/open-8-jdk something). The filename should be something like: openjfx-8u60-sdk-overlay-linux-armv6hf.zip

### Debian/Ubuntu
For Ubuntu Mate there is a package that allows you to install Openjfx directly.

```
sudo apt-get install openjfx
```

## Trouble shooting

### Black frames
If you having problems with black frames or the FrameBuffer doesn't report the correct size you can correct it by setting the disable_overscan=1 in /boot/config.txt. You need to reboot after.

#### Example
tvservice reported that my 7" screen had a prefered mode by 1280x720 @ 60 hz.
```
pi@computer:~ $ tvservice -s
state 0x12000a [HDMI CEA (4) RGB lim 16:9], 1280x720 @ 60.00Hz, progressive
```

but the FrameBuffer driver reported that it only saw:

```
pi@computer:~ $ fbset -s
mode "1184x624"
    geometry 1184 624 1184 624 16
    timings 0 0 0 0 0 0 0
    rgba 5/11,6/5,5/0,0/16
endmode
```

With overscan disabled my framebuffer reported:
```
mode "1280x720"
    geometry 1280 720 1280 720 16
    timings 0 0 0 0 0 0 0
    rgba 5/11,6/5,5/0,0/16
endmode
```

Voila, now the application fills the whole screen i full window mode.