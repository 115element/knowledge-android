package com.example.knowledge_android.comparator.i18n;

import java.util.HashMap;
import java.util.Map;

public class I18n_MobileResource {

    public static Map<String,String> stringResources = new HashMap<>();
    
    static {
        stringResources.put("未获取到服务端主档信息","未获取到服务端主档信息!");
        stringResources.put("云促销计算中", "云促销,计算中...");
        stringResources.put("云促销计算失败", "云促销计算失败");
        stringResources.put("服务端没有返回主档文件信息", "服务端没有返回主档文件信息!");
        stringResources.put("已是最新不需要下载", "已是最新不需要下载!");
        stringResources.put("无法覆盖数据库文件", "无法覆盖数据库文件!");
        stringResources.put("网络原因下载失败", "网络原因,下载失败!");
        stringResources.put("交易成功本次交易小票号", "交易成功,本次交易小票号");
        stringResources.put("超级管理员", "超级管理员");
        stringResources.put("用户名错误", "用户名错误");
    }

    public static Map<String, String> getStringResources() {
        return stringResources;
    }

    public static void setStringResources(Map<String, String> stringResources) {
        I18n_MobileResource.stringResources = stringResources;
    }
}
