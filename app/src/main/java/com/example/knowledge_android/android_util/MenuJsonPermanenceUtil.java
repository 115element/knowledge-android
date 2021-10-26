package com.example.knowledge_android.android_util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;


import static java.util.Objects.requireNonNull;

import com.example.knowledge_android.OneApplication;

//永久存储json值
public class MenuJsonPermanenceUtil {

    public static final String MENU_JSON_FILE_NAME = "menujson.txt";
    public static final String MENU_JSON_DIR = "menu_json";

    @TargetApi(Build.VERSION_CODES.N)
    public static void write(String json) {
        //保存到的文件路径
        //具体路径 : /storage/emulated/0/Android/data/hyi.mobilepos/files/menu_json
        final String filePath = requireNonNull(OneApplication.getInstance().getBaseContext().getExternalFilesDir(MENU_JSON_DIR)).getAbsolutePath();
        FileWriter fw;
        BufferedWriter bw = null;
        try {
            //创建文件夹
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //创建文件
            File file = new File(dir, MENU_JSON_FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            //写入txt文件
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);

            //具体内容
            bw.write(json);
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

    public static String getMenuJsonFilePath() {
        return requireNonNull(OneApplication.getInstance().getBaseContext().getExternalFilesDir(MENU_JSON_DIR)).getAbsolutePath() + File.separator;
    }

    public static File getMenuJsonFile(String fileName) {
        return new File(requireNonNull(OneApplication.getInstance().getBaseContext().getExternalFilesDir(MENU_JSON_DIR)).getAbsolutePath(), fileName);
    }

    public static String getMenuJsonString() {
        String str = "";

        File file = new File(requireNonNull(OneApplication.getInstance().getBaseContext().getExternalFilesDir(MENU_JSON_DIR)).getAbsolutePath(), MENU_JSON_FILE_NAME);
        try {
            FileInputStream in = new FileInputStream(file);
            // size  为字串的长度 ，这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
        return str;
    }

}

