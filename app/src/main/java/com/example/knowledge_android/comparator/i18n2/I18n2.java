package com.example.knowledge_android.comparator.i18n2;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import groovy.transform.CompileStatic;

public class I18n2 {

    public static Locale curLocale;

    public static Map<String, Map> localeResourceMap = new HashMap<>();

    public static Map<String,String> stringResources = new HashMap<>();

    /**
     * 后覆盖前
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setLocaleResources(Map<String, List<Map>> localeResources) {
        if (localeResources == null || localeResources == null || localeResources.size() == 0) {
            return;
        }
        localeResources.forEach((k,v)->{
            Map localeResource = localeResourceMap.get(k);
            if (localeResource == null) {
                localeResource = new HashMap();
            }
            for (Map map : v) {
                localeResource.putAll(map);
            }
            localeResourceMap.put(k,localeResource);
        });
    }

    /**
     * 设置当前locale
     * @param locale
     */
    static void setLocale(Locale locale) {
        curLocale = locale;
        Locale.setDefault(locale);
        //设置语言
        stringResources = localeResourceMap.get(locale.getCountry());
    }

    @CompileStatic
    static String getString(String resourceId, Object... arguments) {
        try {
            if (resourceId.equals("")){
                return "";
            }

            String resourceValue = stringResources.get(resourceId);
            if (resourceValue == null) {
                // 不带参数的汉字，可以不用建string resource
                if (curLocale == Locale.CHINA) {
                    return resourceId;
//                else if (stringResources == stringResources_zh_TW) {
//                    return ZHConverter.convert(resourceId, ZHConverter.TRADITIONAL)
                } else
                    throw new Exception();
            }
            return arguments != null ? MessageFormat.format(resourceValue, arguments) : resourceValue;
        } catch (Exception e) {
            // if cannot be found, return the resourceId itself with question mark
            return resourceId;
        }
    }

    /**
     * 金额格式化
     * @param amount
     * @return
     */
    static String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "";
        }
        String str;
        String symbol;
        if ("TW".equals(Locale.getDefault().getCountry())) {
            symbol = "NT.";
            str = symbol + amount.setScale(0, BigDecimal.ROUND_HALF_DOWN).toString();
        } else {
            symbol = "¥";
            str = symbol + amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
        }
        return str;
    }

    static String getStringForZh(String resourceId) {
        String v = (String) localeResourceMap.get(Locale.CHINA).get(resourceId);
//        if (v == null) {
//            if (stringResources.containsValue(resourceId)) {
//                Map.Entry<String, String> entry = stringResources.find {
//                    it.value == resourceId
//                }
//                if (entry) {
//                    return stringResources_zh_CN[entry.key]
//                }
//            }
//            return ZHConverter.convert(resourceId, ZHConverter.SIMPLIFIED)
//        }
        return v;
    }
}
