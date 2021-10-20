package com.example.knowledge_android.popupwindow;

import com.example.knowledge_android.OneApplication;

public class ScreenUtil {

    public static int getPopupWindowWidth(float percent, int maxWidth) {
        return Math.min((int) (OneApplication.getInstance().getScreenWidth() * percent), maxWidth);
    }

    public static int getPopupWindowHeight(float percent, int maxHeight) {
        return Math.min((int) (OneApplication.getInstance().getScreenHeight() * percent), maxHeight);
    }

}
