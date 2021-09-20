package com.example.knowledge_android.comparator.i18n;

import java.util.HashMap;
import java.util.Map;

public class I18n_MobileResource_zh_TW {
    public static Map<String,String> stringResources = new HashMap<>();
    static {
        stringResources.put("未获取到服务端主档信息","未获取到服务端主档信息!");
        stringResources.put("云促销计算中", "云促销,计算中...");
        stringResources.put("云促销计算失败", "云促销计算失败");
        stringResources.put("服务端没有返回主档文件信息", "服务端没有返回主档文件信息!");
    }
    public static Map<String, String> getStringResources() {
        return stringResources;
    }

    public static void setStringResources(Map<String, String> stringResources) {
        I18n_MobileResource_en.stringResources = stringResources;
    }
}
