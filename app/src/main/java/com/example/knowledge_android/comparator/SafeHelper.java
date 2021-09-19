package com.example.knowledge_android.comparator;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2009-5-15
 * Time: 11:30:42
 * To change this template use File | Settings | File Templates.
 */
public class SafeHelper {

    /**
     * ��ָ�����ı�����MD5ժҪ
     * @return String ժҪ
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String digest(String clearText) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digestText = md.digest(clearText.getBytes());

        Encoder encoder = Base64.getEncoder();
        return encoder.encode(digestText).toString();
    }
    public static String digestHex(String clearText) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digestText = md.digest(clearText.getBytes());
        byte[] bytes = Hex.encode(digestText);
        return new String(bytes);
    }
    /**
     * ��ָ�����ı�����BASE64����
     * @param text String �ı�
     * @return String �ı���BASE64����
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encode(String text) {
         Encoder encoder = Base64.getEncoder();
        return encoder.encode(text.getBytes()).toString();
    }

    /**
     * ȡ��BASE64�����ԭ��
     * @param text String BASE64�����ı�
     * @return String ԭ��
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decode(String text) throws Exception {
        Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(text)).toString();
    }

//    public static void main(String[] args){
//        try {
//            String s =SafeHelper.encrypt("��","12345678");
//            String t=SafeHelper.decrypt("8hiPHHYMgKU=","12345678") ;
//            System.out.println("s = " + s);
//            System.out.println("t = " + t);
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
}