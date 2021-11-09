package com.example.knowledge_android.volley;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.OneApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//1. 主线程，显示提示视图
//2. 启动分线程，请求下载APK文件，下载过程中显示下载进度。
//3. 主线程，移除视图，启动安装

public class DownLoadApk extends AppCompatActivity {

    ProgressBar progressBar = new ProgressBar(OneApplication.getInstance().getBaseContext());

    //准备用于保存APK文件的File对象：/storage/sdcard/Android/package_name/file/xx.apk
    File apkFile;


    public void downLoadApk(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    apkFile = new File(getExternalFilesDir(null),"update.apk");

                    //1、得到连接对象
                    String path = "http://192.168.0.1:8080/web/app.apk";
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //2.设置
                    //connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(100000);
                    //3.连接
                    connection.connect();
                    //4.请求并得到响应码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        //设置dialog的最大值
                        progressBar.setMax(connection.getContentLength());

                        //5.得到包含Apk文件数据的InputStream
                        InputStream is = connection.getInputStream();
                        //6.创建指向apkFile的FileOutputStream
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        //7.边读边写
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer,0,len);
                            //8.显示下载进度
                            progressBar.incrementProgressBy(len);

                            //休息一会(模拟网速慢)
                            //Thread.sleep(50);
                            SystemClock.sleep(50); //和上边的区别就是已经处理了异常
                            //SystemClock.currentThreadTimeMillis();
                        }

                        fos.close();
                        is.close();
                    }
                    //9.下载完成，关闭
                    connection.disconnect();

                    //10.主线程，移除进度条，启动安装
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 关闭progressBar
                            installApk();
                        }
                    });

                } catch (Exception e){
                }
            }
        }).start();
    }


    //启动安装APK
    private void installApk() {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
        startActivity(intent);
    }
}
