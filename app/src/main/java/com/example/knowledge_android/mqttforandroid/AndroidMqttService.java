package com.example.knowledge_android.mqttforandroid;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;

import com.example.knowledge_android.R;
import com.example.knowledge_android.daosupport.bean.tran.TranDetail;
import com.example.knowledge_android.fragment.pos_screen.PosScreenMainActivity;
import com.example.knowledge_android.knowledge.DevAddrUtil;
import com.example.knowledge_android.knowledge.ZipUtil;
import com.example.knowledge_android.mqttforandroid.okhttp.PostWithFromDataUtil;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

//TODO 以下3个jar包需要 调整版本为1.59，才有这几个类，现在用的1.47，因为一个标准的MQTT用法也在程序包目录中，两个版本类有调整，不能同时使用
//import org.bouncycastle.openssl.PEMKeyPair;
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


/**
 * ssl校验方式的mqtt
 */

/*
用法说明：在OneApplication中添加以下代码，其中mainActivity，为主Activity

//mqtt调用(进行主题订阅)
 log.info("start Android Mqtt");
 AndroidMqttService androidMqttService = new AndroidMqttService();
 String clientId = "pos_" + Param.getInstance().getStoreId() + "_" + DevAddrUtil.getLocalMacIdFromIp();//规则:
 List<String> topicList = new ArrayList<>();
 topicList.add("pos_sale_status_" + Param.getInstance().getStoreId());//菜单更新mqtt
 topicList.add("pos_log_upload_" + Param.getInstance().getStoreId());//菜单更新mqtt
 androidMqttService.connectMqttService(mainActivity, topicList, "ssl://121.5.25.230:8883", clientId, "");

 */

public class AndroidMqttService {

    //ssl签名文件校验
    public static SSLSocketFactory getSocketFactory(InputStream caCrtFile,
                                                    InputStream crtFile,
                                                    InputStream keyFile,
                                                    String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        X509Certificate caCert = null;

        BufferedInputStream bis = new BufferedInputStream(caCrtFile);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            caCert = (X509Certificate) cf.generateCertificate(bis);
        }

        // load client certificate
        bis = new BufferedInputStream(crtFile);
        X509Certificate cert = null;
        while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
        }


        //TODO 真正使用该服务时，把以下注释打开，在build.gradle将org.bouncycastle.openssl版本调整为1.59就可以用了
        // load client private cert
//        PEMParser pemParser = new PEMParser(new InputStreamReader(keyFile));
//        Object object = pemParser.readObject();
//        //核心:安卓P以上版本不再支持"BC算法"
          KeyPair key = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//            key = converter.getKeyPair((PEMKeyPair) object);
//        } else {
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//            key = converter.getKeyPair((PEMKeyPair) object);
//        }

        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("cert-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-cert", key.getPrivate(), password.toCharArray(),
                new java.security.cert.Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }


    public void connectMqttService(PosScreenMainActivity mainActivity, List<String> topicList, String serverURI, String clientId, String password) throws Exception {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        MqttAndroidClient mqttAndroidClient = new MqttAndroidClient(mainActivity.getApplicationContext(), serverURI, clientId);
        //在src/main/res/raw下的ssl签名 , 文件名分别为:ca.pem(caCrtFile) , client.pem(crtFile) , key.key(keyFile)
        InputStream caCrtFile = mainActivity.getResources().openRawResource(R.raw.ca);
        InputStream crtFile = mainActivity.getResources().openRawResource(R.raw.client);
        InputStream keyFile = mainActivity.getResources().openRawResource(R.raw.key);
        SSLSocketFactory sslSocketFactory = getSocketFactory(caCrtFile, crtFile, keyFile, password);

        mqttConnectOptions.setSocketFactory(sslSocketFactory);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(null);
        mqttConnectOptions.setPassword(null);
        mqttConnectOptions.setConnectionTimeout(30);  //超时时间
        mqttConnectOptions.setKeepAliveInterval(60); //心跳时间,单位秒
        mqttConnectOptions.setAutomaticReconnect(true);//自动重连
        mqttAndroidClient.connect(mqttConnectOptions);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                for (String s : topicList) {
                    subscribeToTopic(mqttAndroidClient, s);
                }
                Log.e(TAG, "reconnect ---> " + reconnect + "       serverURI--->" + serverURI);
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.e(TAG, "cause ---> " + cause);
            }

            //核心函数 , 接收主题和信息
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.e(TAG, "topic ---> " + topic + "\nmessage--->" + message);
                //将此处的topic 和 message传到处理消息信息的函数中去
                //==================================
                LogUtil.writerLog("log_1970_01_01.txt", message.toString());
                //==================================
                if (topic.equals("pos_log_upload_" + "208888")) {//被动做日志上传
                    //PostBeiRuiUrlUtil.doUploadLogForMqtt();
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.e(TAG, "token ---> " + token);
            }
        });
    }


    //在connectComplete下进行订阅主题
    public void subscribeToTopic(MqttAndroidClient mqttAndroidClient, String subscriptionTopic) {
        try {

            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e(TAG, "onFailure ---> " + asyncActionToken);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "onFailure ---> " + exception);
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, "subscribeToTopic is error");
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    public static void doUploadLog(boolean isScheduled) throws Exception {
        @SuppressLint("SimpleDateFormat") String endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String timestamp = Long.valueOf(System.currentTimeMillis()).toString().substring(0, 10);
        String zipFileName = "30000" + "_" + "LogFileStartTime" + "_" + endTime + "_" + timestamp + ".zip";
        File zipFile = ZipUtil.ZipFolder(LogUtil.getLogfilePath() + "log_1970_01_01.txt", "log_zip", zipFileName);
        //两个请求参数
        String token = "X87X86"; //现在直接读数据库中的token值
        File file = zipFile;
        String fileName = zipFileName;
        String macIp = DevAddrUtil.getLocalMacIdFromIp();
        String url = "http://www.chinafamilymart.com.cn:9999" + "/ms/postablet/log/uploadfile";

        //使用OKHttp上传日志
        PostWithFromDataUtil.doPostForFromData(token, file, fileName, macIp, url);

    }


    @TargetApi(Build.VERSION_CODES.N)
    public static void handleRsp(TranDetail rsp5, File zipFile) throws Exception {
        String logFileName = zipFile.getName();

        if (rsp5 != null) {
            if (rsp5.getId() == 1) {
                Log.e(TAG, "调用日志上传接口成功");
                boolean isDeleteSuccessful = zipFile.delete();
                if (isDeleteSuccessful) {
                    Log.e(TAG, "文件" + zipFile + "删除成功");
                } else {
                    Log.e(TAG, "主动上传成功" + zipFile + "删除失败");
                }
            } else {
                Log.e(TAG, "调用日志上传接口成功,返回错误信息:" + rsp5.getStoreId());
                LogUtil.writerLog(logFileName, "调用日志上传接口成功,返回错误信息:" + rsp5.getStoreId());
            }
        } else {
           Log.i("TAG","失败调用");
        }
    }
}