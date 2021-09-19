package com.example.knowledge_android.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubcribeCallBack implements MqttCallbackExtended {

    private String t = "FF";

    @Override
    public void connectionLost(Throwable throwable) {
        Log.i(t,"client lost connection,reconnecting");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        try {
            // subscribe后得到的消息会执行到这里面
            Log.i(t,"接收消息主题 : " + s);
            Log.i(t,"接收消息Qos : " + mqttMessage.getQos());
            Log.i(t,"接收消息内容 : " + new String(mqttMessage.getPayload()));
            String str = mqttMessage.toString();
            Log.i(t,"从MQTT收到的消息为:" + str + " ;TOPIC:" + s);
        } catch (Exception e) {
            Log.i(t,"SubcribeCallBack error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.i(t,"deliveryComplete---------" + iMqttDeliveryToken.isComplete());
    }

    @Override
    public void connectComplete(boolean b, String s) {
        Log.i(t,"receive connectted");
    }
    
}
