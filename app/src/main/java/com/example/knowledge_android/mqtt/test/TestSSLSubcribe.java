package com.example.knowledge_android.mqtt.test;


import com.example.knowledge_android.mqtt.MQTTSubcribeClient;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

public class TestSSLSubcribe {

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1.0,SSLv3");
        MQTTSubcribeClient mqttReceiver = new MQTTSubcribeClient("ssl://121.5.25.230:8883", String.valueOf(Math.random()));
    }
}