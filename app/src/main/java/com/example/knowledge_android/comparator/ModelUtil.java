package com.example.knowledge_android.comparator;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Bruce on 2018/04/08.
 */
public class ModelUtil {

    //获取1970-01-01
    public static Date getInitialDate() {
        Date initDate = new Date(0);
        int timeOffset = 0;
        TimeZone timeZone = Calendar.getInstance().getTimeZone();
        timeOffset = timeOffset - timeZone.getRawOffset();
        initDate.setTime(timeOffset);
        return initDate;
    }

    public static Date nDaysAgo(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -n);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return c.getTime();
    }

    //计算一串字符有多少汉字
    /*static int getChineseCount(String name) {
        String regEx = "[\\u4e00-\\u9fa5]"
        Pattern p = Pattern.compile(regEx)
        Matcher m = p.matcher(name)
        int count = 0//计算汉字有多少个
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        count
    }*/

    //判断金额小数位是否为两位
    /*static boolean decimalIsBit(String amount) {
        StringBuilder sb = new StringBuilder(amount)
        int pointIndex = sb.indexOf(".")  //返回小数点在此字符串中第一次出现处的索引
        *//*整个字符串总长 - 小数点出现的脚标 - 1 = 就等于小数点后边有几位数*//*
        if (pointIndex >=0 && (sb.length() - pointIndex - 1) > 2) { //大于两位
            return false
        }
        return true
    }*/

    public static String uncapitalize(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            char firstChar = str.charAt(0);
            return Character.isLowerCase(firstChar) ? str : (new StringBuilder(strLen)).append(Character.toLowerCase(firstChar)).append(str.substring(1)).toString();
        } else {
            return str;
        }
    }
}
