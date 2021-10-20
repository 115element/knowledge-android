package com.example.knowledge_android.popupwindow;

import android.content.Context;
import android.widget.PopupWindow;

import com.example.knowledge_android.widget.builder.GViewBuilder;

public class DiyPopupWindow extends PopupWindow {

    public DiyPopupWindow(Context context) {
        //根据设备屏幕大小，设置弹窗PopupWindow的大小。
        super(ScreenUtil.getPopupWindowWidth(0.9f, GViewBuilder.stringToPixels("1000dp")),
                ScreenUtil.getPopupWindowHeight(0.9f, GViewBuilder.stringToPixels("700dp")));
    }
}
