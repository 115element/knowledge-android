package com.example.knowledge_android.comparator.i18n2;

import java.util.HashMap;
import java.util.Map;

public class I18n_CommonResource_en {
    public static Map<String,String> stringResources = new HashMap<>();
    static {
        stringResources.put("数字","数字: {0,number,#,##0.00}");
        stringResources.put("钱钱", "钱钱: {0,number,currency}");
        stringResources.put("倒数计秒" , "{0} ({1}秒...)");
        stringResources.put("登录","登录");
    }

    public static Map<String, String> getStringResources() {
        return stringResources;
    }

    public static void setStringResources(Map<String, String> stringResources) {
        I18n_CommonResource_en.stringResources = stringResources;
    }
}
