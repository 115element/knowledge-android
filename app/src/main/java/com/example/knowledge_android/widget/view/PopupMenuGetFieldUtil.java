package com.example.knowledge_android.widget.view;

import android.annotation.SuppressLint;
import android.view.Menu;

import java.lang.reflect.Method;

/**
 * 通过反射机制暴力修改com.android.internal.view.menu.MenuBuilder ,强制PopupMenu可以设置图标
 */
public class PopupMenuGetFieldUtil {

    public static void setIconEnable(Menu menu, boolean enable) {
        try {
            @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            @SuppressLint("DiscouragedPrivateApi") Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            // MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
