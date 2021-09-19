package com.example.knowledge_android.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublishCallback implements MqttCallbackExtended {

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        Log.i("FF","[PublishCallback] 连接断开");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.i("FF","deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    }

    @Override
    public void connectComplete(boolean b, String s) {
        Log.i("FF","connected");
    }
}
