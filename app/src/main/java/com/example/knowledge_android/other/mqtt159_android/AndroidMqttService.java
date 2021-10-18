//package com.example.knowledge_android.other.mqtt159_android;
//
//import static android.content.ContentValues.TAG;
//
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.util.Log;
//
//import com.example.knowledge_android.tablayout_noscrollviewpager.MainActivity;
//import com.example.knowledge_android.R;
//import com.example.knowledge_android.other.utils.LogUtil;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMKeyPair;    //需要版本1.59   POS上的是1.47
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import org.eclipse.paho.android.service.MqttAndroidClient;
//import org.eclipse.paho.client.mqttv3.IMqttActionListener;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.IMqttToken;
//import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.KeyPair;
//import java.security.KeyStore;
//import java.security.Security;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.List;
//import java.util.Objects;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManagerFactory;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//
///**
// * 2021/8/23
// * <p>
// * ssl校验方式的mqtt
// * <p>
// */
//public class AndroidMqttService {
//
//    //ssl签名文件校验
//    public static SSLSocketFactory getSocketFactory(InputStream caCrtFile,
//                                                    InputStream crtFile,
//                                                    InputStream keyFile,
//                                                    String password) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
//
//        // load CA certificate
//        X509Certificate caCert = null;
//
//        BufferedInputStream bis = new BufferedInputStream(caCrtFile);
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//
//        while (bis.available() > 0) {
//            caCert = (X509Certificate) cf.generateCertificate(bis);
//        }
//
//        // load client certificate
//        bis = new BufferedInputStream(crtFile);
//        X509Certificate cert = null;
//        while (bis.available() > 0) {
//            cert = (X509Certificate) cf.generateCertificate(bis);
//        }
//
//        // load client private cert
//        PEMParser pemParser = new PEMParser(new InputStreamReader(keyFile));
//        Object object = pemParser.readObject();
//        //核心:安卓P以上版本不再支持"BC算法"
//        KeyPair key;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//            key = converter.getKeyPair((PEMKeyPair) object);
//        } else {
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//            key = converter.getKeyPair((PEMKeyPair) object);
//        }
//
//        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
//        caKs.load(null, null);
//        caKs.setCertificateEntry("cert-certificate", caCert);
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(caKs);
//
//        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//        ks.load(null, null);
//        ks.setCertificateEntry("certificate", cert);
//        ks.setKeyEntry("private-cert", key.getPrivate(), password.toCharArray(),
//                new java.security.cert.Certificate[]{cert});
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(ks, password.toCharArray());
//
//        SSLContext context = SSLContext.getInstance("TLSv1.2");
//        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//
//        return context.getSocketFactory();
//    }
//
//
//    public void connectMqttService(MainActivity mainActivity, List<String> topicList, String serverURI, String clientId, String password) throws Exception {
//        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
//        MqttAndroidClient mqttAndroidClient = new MqttAndroidClient(mainActivity.getApplicationContext(), serverURI, clientId);
//        //在src/main/res/raw下的ssl签名 , 文件名分别为:ca.pem(caCrtFile) , client.pem(crtFile) , key.key(keyFile)
//        InputStream caCrtFile = mainActivity.getResources().openRawResource(R.raw.ca);
//        InputStream crtFile = mainActivity.getResources().openRawResource(R.raw.client);
//        InputStream keyFile = mainActivity.getResources().openRawResource(R.raw.key);
//        SSLSocketFactory sslSocketFactory = getSocketFactory(caCrtFile, crtFile, keyFile, password);
//
//        mqttConnectOptions.setSocketFactory(sslSocketFactory);
//        mqttConnectOptions.setAutomaticReconnect(true);
//        mqttConnectOptions.setCleanSession(false);
//        mqttConnectOptions.setUserName(null);
//        mqttConnectOptions.setPassword(null);
//        mqttConnectOptions.setConnectionTimeout(30);  //超时时间
//        mqttConnectOptions.setKeepAliveInterval(60); //心跳时间,单位秒
//        mqttConnectOptions.setAutomaticReconnect(true);//自动重连
//        mqttAndroidClient.connect(mqttConnectOptions);
//        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
//            @Override
//            public void connectComplete(boolean reconnect, String serverURI) {
//                for (String s : topicList) {
//                    subscribeToTopic(mqttAndroidClient, s);
//                }
//                Log.e(TAG, "reconnect ---> " + reconnect + "       serverURI--->" + serverURI);
//            }
//
//            @Override
//            public void connectionLost(Throwable cause) {
//                Log.e(TAG, "cause ---> " + cause);
//            }
//
//            //核心函数 , 接收主题和信息
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                Log.e(TAG, "topic ---> " + topic + "\nmessage--->" + message);
//                //将此处的topic 和 message传到处理消息信息的函数中去
//                //==================================
//                LogUtil.writerLog("log_1970_01_01.txt", message.toString());
//                //==================================
//                if (topic.equals("pos_log_upload_" + "207777")) {//被动做日志上传
//                    doUploadLogForMqtt();
//                }
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//                Log.e(TAG, "token ---> " + token);
//            }
//        });
//    }
//
//
//    @TargetApi(Build.VERSION_CODES.N)
//    public static void doUploadLogForMqtt() throws Exception {
//        //===========================================================================
//        try {
//            RequestBody fileBody = RequestBody.create(MediaType.parse("application/from-data"), new File("日志文件路径"));
//
//            RequestBody requestBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("file", "文件名", fileBody)
//                    .addFormDataPart("fileName", "文件名")
//                    .addFormDataPart("macIp", "macIp")
//                    .build();
//            Request request = new Request.Builder()
//                    .addHeader("posToken", "令牌")//添加请求头
//                    .url("服务地址")//添加URl
//                    .post(requestBody)//添加body
//                    .build();
//            OkHttpClient okHttpClient = new OkHttpClient();
//            Call call = okHttpClient.newCall(request);
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    //请求失败的处理
//                    Log.e(TAG, "失败" + e);
//                    LogUtil.writerLog("日志文件名", "调用日志上传接口失败" + e);
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) {
//                    try {
//                        String rsp = Objects.requireNonNull(response.body()).string();
//                        Log.e(TAG, "成功" + rsp);
//                        ServerRes res = new Gson().fromJson(rsp, new TypeToken<ServerRes>() {
//                        }.getType());
//                        if (res.getCode().equals("200")) {
//                            Log.i("TAG","上传成功，执行你的逻辑即可");
//                        }
//                    } catch (Exception e) {
//                        Log.e(TAG, "onResponse异常" + e);
//                    }
//                    //异步多线程的同步传到主线程
////                runOnUiThread(() ->
//                    //        BeiRuiUploadFileRsp rsp5 = beiRuiClient5.receiveBeiRuiUploadFileData(beiRuiUploadFileReq);
////                        object = new Gson().fromJson(rsp, new TypeToken<BeiRuiUploadFileRsp>() {
////                        }.getType())
////                );
//
//                }
//            });
//        } catch (Exception e) {
//            Log.e(TAG, "doPostForFromData异常" + e);
//        }
//
//    }
//
//
//    //在connectComplete下进行订阅主题
//    public void subscribeToTopic(MqttAndroidClient mqttAndroidClient, String subscriptionTopic) {
//        try {
//
//            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    Log.e(TAG, "onFailure ---> " + asyncActionToken);
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Log.e(TAG, "onFailure ---> " + exception);
//                }
//            });
//        } catch (MqttException e) {
//            Log.e(TAG, "subscribeToTopic is error");
//            e.printStackTrace();
//        }
//    }
//}