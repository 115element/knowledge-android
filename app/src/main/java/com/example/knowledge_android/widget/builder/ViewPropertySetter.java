package com.example.knowledge_android.widget.builder;

import android.view.View;

import com.example.knowledge_android.widget.view.Banner;
import com.example.knowledge_android.widget.view.MarqueeText;

public class ViewPropertySetter {

    //static boolean logCalls;
    
    public static boolean setPropertyForViewBySetterWithoutContext(View view, String setterName, Object value) {
        //long now
        //if (logCalls) {
        //    log.info("[DynaCall] ${view.getClass().getName()}->$setterName(${value ? value.getClass().getName() : "null"})")
        //    now = new Date().time
        //}

        try {
            Class viewClass = view.getClass();
            // Using switch statement is much slower then if-else !
            if (viewClass == Banner.class) {
                if (setterName.equals("setBackgroundResource")) { ((Banner) view).setBackgroundResource((int) value); return true; }
                if (setterName.equals("setTextSize")) { ((Banner) view).setTextSize(toInt(value)); return true; }
                if (setterName.equals("setTextColor")) { ((Banner) view).setTextColor((int) value); return true; }
            } else if (viewClass == MarqueeText.class) {
                if (setterName.equals("setTextColor")) { ((MarqueeText) view).setTextColor((int) value); return true; }
                if (setterName.equals("setTextSize")) {
                    if (value instanceof Integer)
                        ((MarqueeText) view).setTextSize((int) value);
                    else
                        ((MarqueeText) view).setTextSize(toFloat(value));
                    return true;
                }
            }
            //log.info("[DynaCall] ${view.getClass().getName()}->$setterName(${value ? value.getClass().getName() : "null"})")
            //log.info("[DynaCall] setPropertyForViewBySetterDynamicallyWithoutContext")
            return false;
        } finally {
            //if (logCalls)
            //    log.info("[DynaCall] return ${new Date().time - now}ms")
        }
    }

    static boolean toBoolean(Object value) {
        if (value instanceof Boolean)
            return (Boolean) value;
        else if (value instanceof Integer)
            return (Integer) value != 0;
        else
            return false;
    }

    static int toInt(Object value) {
        if (value instanceof Integer)
            return (Integer) value;
        else if (value instanceof Float)
            return ((Float) value).intValue();
        else if (value instanceof Double)
            return ((Double) value).intValue();
        else if (value instanceof String)
            return Integer.parseInt((String) value);
        else
            throw new RuntimeException("Failed toInt(" + value + ")");
    }

    static float toFloat(Object value) {
        if (value instanceof Integer)
            return ((Integer) value).floatValue();
        else if (value instanceof Float)
            return (Float) value;
        else if (value instanceof Double)
            return ((Double) value).floatValue();
        else if (value instanceof String)
            return Float.parseFloat((String) value);
        else
            throw new RuntimeException("Failed toFloat(" + value + ")");
    }
}
