package com.example.knowledge_android.mqttforandroid.okhttp;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.knowledge_android.daosupport.bean.tran.TranDetail;
import com.example.knowledge_android.mqttforandroid.AndroidMqttService;
import com.example.knowledge_android.mqttforandroid.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 使用OKHttp上传文件
 */

public class PostWithFromDataUtil extends Exception {

    @NotNull
    public static void doPostForFromData(String token,File file,String fileName, String macIp,String url) {
        try {
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/from-data"), file);

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), fileBody)
                    .addFormDataPart("fileName", fileName)
                    .addFormDataPart("macIp", macIp)
                    .build();
            Request request = new Request.Builder()
                    .addHeader("posToken", token)//添加请求头
                    .url(url)//添加URl
                    .post(requestBody)//添加body
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //请求失败的处理
                    Log.e(TAG, "失败" + e);
                    LogUtil.writerLog("log_1970_01_01.txt", "调用日志上传接口失败" + e);
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        String rsp = Objects.requireNonNull(response.body()).string();
                        Log.e(TAG, "成功" + rsp);

                        //这里演示返回值处理
                        TranDetail tranDetail = new Gson().fromJson(rsp, new TypeToken<TranDetail>() {}.getType());

                        AndroidMqttService.handleRsp(tranDetail, file);
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse异常" + e);
                        LogUtil.writerLog("1970.zip", "onResponse异常" + e);
                    }

                    //异步多线程的同步传到主线程
//                runOnUiThread(() ->
                    //        BeiRuiUploadFileRsp rsp5 = beiRuiClient5.receiveBeiRuiUploadFileData(beiRuiUploadFileReq);
//                        object = new Gson().fromJson(rsp, new TypeToken<BeiRuiUploadFileRsp>() {
//                        }.getType())
//                );

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "doPostForFromData异常" + e);
            LogUtil.writerLog("log_1970_01_01.txt", "doPostForFromData异常" + e);
        }
    }
}
