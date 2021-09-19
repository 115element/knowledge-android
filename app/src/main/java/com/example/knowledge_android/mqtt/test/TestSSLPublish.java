package com.example.knowledge_android.mqtt.test;

import com.alibaba.fastjson.JSON;
import com.example.knowledge_android.mqtt.MQTTPublishClient;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.net.ssl.SSLContext;
import java.util.HashMap;
import java.util.Map;

public class TestSSLPublish {

    public static void main(String[] args) throws Exception {
        //mqtt发送端
        MQTTPublishClient mqttClientSend = new MQTTPublishClient("ssl://121.5.25.230:8883", String.valueOf(Math.random()));
        MqttMessage message = new MqttMessage();
        Map msg = new HashMap<String,String>();
        msg.put("tag", "hello  ssl");
        message.setPayload(JSON.toJSONString(msg).getBytes("UTF-8"));
        message.setQos(0);
        message.setRetained(false);
        System.out.println("MQTT发送消息");
        mqttClientSend.publish("/ssl",message);
    }
}
