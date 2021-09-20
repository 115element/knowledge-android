package com.example.knowledge_android.knowledge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.knowledge_android.number_popupwindow.ViewAndWindow;

public class AndroidDrawableUtil {

    //======xml点击按钮弹出popupwindow的方法==========================================================

    /**
     * @param view : 布局对象
     * @param v    : 相对控件对象
     * @param xoff : x轴向右(+)偏移
     * @param yoff : y轴向上(+)偏移
     */
    public static ViewAndWindow drawPopupWindow(View view, View v, int resource, int xoff, int yoff) {
        /**
         *
         * 既然是把viewGroup给顶掉了。没有父布局，那加一个viewGroup就行了，可以在布局中加，也可以在设置popupWindow中
         *在将布局文件infate的时候，给它加一个viewGroup，再把这个view设置给pop，这个LayoutInflater.inflater第二个参数本来就是ViewGroup，只是一般把他写成了null
         *
         * */
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        View inflateForCalculation = inflater.inflate(resource, linearLayout);
        inflateForCalculation.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = inflateForCalculation.getMeasuredHeight();
        int width = inflateForCalculation.getMeasuredWidth();

        View inflate = View.inflate(view.getContext(), resource, null);

        ViewAndWindow viewAndWindow = new ViewAndWindow();

        viewAndWindow.setView(inflate);

        //  创建PopupWindow对象 不限定高度和宽度
        PopupWindow window = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        viewAndWindow.setPopupWindow(window);


        // TODO:  设置可以获取焦点
        window.setFocusable(true);
        // TODO:  设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        window.setTouchable(true);


        //通过计算得出了popupwindow的宽和高的 wrap_content 传入
        window.setWidth(width);//控制popupwindow宽度
        window.setHeight(height);//控制popupwindow高度

        window.showAsDropDown(v, xoff, yoff);//指定组件v下弹出 ,Gravity.BOTTOM在组件下方

        return viewAndWindow;
    }
}



