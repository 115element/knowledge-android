package com.example.knowledge_android.comparator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PosUtil {

    public static Gson createGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
    }

    //电子发票二维码数据生成
    static String getQrCodeMessage() {
        String strs = "";
        try {
            //http://devlawson.api.yorentick.cn/appmodules/einvoice/lawson/?ticket=${invioceParameter}"
            String url ="https://pp.yoren.com/appmes/einvoice/lawson/"; //= Param.instance.eInvoiceUrl;
            String key ="8888888"; //= Param.instance.officialEInvoiceKey;
            String str = "${url}?ticket=";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            DecimalFormat df = new DecimalFormat("########0.00");
            String money = df.format(new BigDecimal(27));

            String str2 = "${tranHead.storeId},${sdf.format(tranHead.systemDate)},${tranHead.getOrderNumber()},${money}";
            byte[] bytes = MyDES.encrypt(str2.getBytes("utf-8"), key);
            String strEncode = MyDES.encryptBASE64(bytes);

            strEncode = strEncode.replace("\r\n", "");  // 解决Base64添加换行符的问题 【游仁使用Java基础包的Base64  我们使用阿帕奇的Base64】

            strs = str + URLEncoder.encode(strEncode, "utf-8");
            return strs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }
}
