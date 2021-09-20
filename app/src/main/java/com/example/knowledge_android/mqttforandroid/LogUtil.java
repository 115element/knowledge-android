package com.example.knowledge_android.mqttforandroid;

import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import com.example.knowledge_android.OneApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class LogUtil {
    @TargetApi(Build.VERSION_CODES.N)
    public static void writerLog(String logFileName, String msg) {
        //保存到的文件路径
        //具体路径 : /storage/emulated/0/Android/data/hyi.mobilepos/files/log
        final String filePath = requireNonNull(OneApplication.getInstance().getBaseContext().getExternalFilesDir("log")).getAbsolutePath();
        FileWriter fw;
        BufferedWriter bw = null;
        try {
            //创建文件夹
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //创建文件
            File file = new File(dir, logFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //写入日志文件
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sim = dateFormat.format(date);
            bw.write(sim + "   " + msg + "\n");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getLogfilePath() {
        return requireNonNull(OneApplication.getInstance().getBaseContext().getExternalFilesDir("log")).getAbsolutePath() + File.separator;
    }

    public static File getLogfile(String logFileName) {
        return new File(requireNonNull(OneApplication.getInstance().getBaseContext().getExternalFilesDir("log")).getAbsolutePath(), logFileName);
    }
}

