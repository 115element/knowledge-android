package com.example.knowledge_android.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



public class MQTTPublishClient {

    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public String HOST;
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private String clientid;

    private MqttClient client;

    private MqttTopic mqttTopic;

    /**
     * 构造函数
     * @throws MqttException
     */
    public MQTTPublishClient(String host, String serverId) {
        Log.i("TAG","MQTTPublishClient instance");
        HOST = host;
        clientid = serverId;
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        try {
            if (client == null) {
                client = new MqttClient(HOST, clientid, new MemoryPersistence());
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        connect();
    }

    /**
     *  用来连接服务器
     */
    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        // 设置超时时间
        options.setConnectionTimeout(20);
        // 设置会话心跳时间
        options.setKeepAliveInterval(10);
        options.setAutomaticReconnect(true);//设置自动重连
        try {
            options.setSocketFactory(SslUtil.getSocketFactory(System.getProperty("user.dir") + "\\ca\\ca.pem", System.getProperty("user.dir") + "\\ca\\client.pem", System.getProperty("user.dir") + "\\ca\\client.key", ""));
            client.setCallback(new PublishCallback());
            client.connect(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送消息并获取回执
    public void publish(String topic, MqttMessage message) throws MqttPersistenceException,
            MqttException, InterruptedException {
        Log.i("TAG","publish   topic:  " + topic);
        mqttTopic = client.getTopic(topic);
        MqttDeliveryToken token = mqttTopic.publish(message);
        token.waitForCompletion();
        Log.i("TAG","message is published completely! "
                + token.isComplete());
        Log.i("TAG","messageId:" + token.getMessageId());
        token.getResponse();
        /*if (client.isConnected())
            client.disconnect(10000);*/
        Log.i("FF","Disconnected: delivery token \"" + token.hashCode()
                + "\" received: " + token.isComplete());
    }
}
