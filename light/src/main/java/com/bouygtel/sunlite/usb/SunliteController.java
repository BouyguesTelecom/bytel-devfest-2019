package com.bouygtel.sunlite.usb;

import exentop.sunlite.SunliteJNI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SunliteController {

    private static Logger LOGGER = LoggerFactory.getLogger(SunliteController.class);

    private char[] channels = new char[512];

    @PostConstruct
    public void initialisation() {
        LOGGER.info("Connexion au boitier Sunlite...");
        SunliteJNI.load();
        SunliteJNI.init();
        SunliteJNI.open();
        LOGGER.info("Connexion au boitier OK");
        send();
    }

    public void setValue(int i, char value) {
        channels[i] = value;
    }

    public void send() {
        SunliteJNI.dmxOut(channels);
    }

    @PreDestroy
    public void fermeture() {
        LOGGER.info("Fermeture de la connexion au boitier Sunlite");
        SunliteJNI.close();
    }

}
