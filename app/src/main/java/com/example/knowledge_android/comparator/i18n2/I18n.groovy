package com.example.knowledge_android.comparator.i18n2

import groovy.transform.CompileStatic

import java.text.MessageFormat
import java.text.NumberFormat

class I18n {

    static Locale curLocale

    static Map<String, Map> localeResourceMap = new HashMap()

    static Map stringResources = new HashMap()

    /**
     * 后覆盖前
     * @param locale
     * @param resources
     */
    static void setLocaleResources(Map<String, List<Map>> localeResources) {
        if (localeResources == null || localeResources == null || localeResources.size() == 0) {
            return
        }
        localeResources.each {
            Map localeResource = localeResourceMap.get(it.key)
            if (localeResource == null) {
                localeResource = new HashMap()
            }
            for (Map resource : it.value) {
                localeResource.putAll(resource)
            }
            localeResourceMap.put(it.key, localeResource)
        }
    }

    /**
     * 设置当前locale
     * @param locale
     */
    static void setLocale(Locale locale) {
        curLocale = locale
        Locale.setDefault(locale)
        //设置语言
        stringResources = localeResourceMap.get(locale.getCountry())
    }

    @CompileStatic
    static String getString(String resourceId, Object... arguments = null) {
        try {
            if (resourceId == '')
                return ''
            String resourceValue = stringResources[resourceId]
            if (resourceValue == null) {
                // 不带参数的汉字，可以不用建string resource
                if (curLocale == Locale.CHINA) {
                    return resourceId
//                else if (stringResources == stringResources_zh_TW) {
//                    return ZHConverter.convert(resourceId, ZHConverter.TRADITIONAL)
                } else
                    throw new Exception()
            }
            arguments ? MessageFormat.format(resourceValue, arguments) : resourceValue
        } catch (e) {
            // if cannot be found, return the resourceId itself with question mark
            resourceId
        }
    }

    /**
     * 金额格式化
     * @param amount
     * @return
     */
    static String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return ""
        }
        String str
        String symbol
        if ('TW'.equals(Locale.getDefault().getCountry())) {
            symbol = 'NT.'
            str = symbol + amount.setScale(0, BigDecimal.ROUND_HALF_DOWN).toString()
        } else {
            symbol = '¥'
            str = symbol + amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString()
        }
        return str
    }

    static String getStringForZh(String resourceId) {
        String v = localeResourceMap.get(Locale.CHINA)[resourceId]
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
        return v
    }
}
