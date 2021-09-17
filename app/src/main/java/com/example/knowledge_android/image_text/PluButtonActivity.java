package com.example.knowledge_android.image_text;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

import java.math.BigDecimal;

/**
 * @author alex
 * create on 2021-09-17
 * 实现一个自定义Button上半部分显示图片，下半部分显示文字描述，并且Button右上角绘制销售数量。
 */
public class PluButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PluButtonView pluButtonView = new PluButtonView(this);

        setContentView(pluButtonView);

        pluButtonView.setBackgroundColor(Color.YELLOW);


        //设置边框
        pluButtonView.setBackgroundResource(R.drawable.button_style);


        //文字如何换行
        StringBuilder sb = new StringBuilder();
        sb.append("如果文字换行使用br,使用\\n会有问题");
        sb.append("<br>");
        sb.append("<font color=\"#ff0000\">");
        sb.append("图片描述2");
        sb.append("</font>");
        pluButtonView.setText(Html.fromHtml(sb.toString()));


        //字体大小为16，并且加粗
        pluButtonView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        pluButtonView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        //取消加粗
        //pluButtonView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        pluButtonView.setSingleLine(false);
        pluButtonView.setMinLines(1);
        pluButtonView.setMaxLines(4);
        pluButtonView.setPadding(2, 2, 2, 2);
        //TODO 格式可能跟PC版不一樣
        pluButtonView.setGravity(Gravity.LEFT);

        //TODO 一句代码值千金
        //pluButtonView.setBackgroundResource(R.drawable.top);
        pluButtonView.setBitmap(R.drawable.plu);

        //如何在代码中设置setCompoundDrawables，上下左右图片
//            Drawable drawable = getResources().getDrawable(R.drawable.top);
//            int x = drawable.getMinimumWidth();
//            int y = drawable.getMinimumHeight();
//            PosLog.info("x:" + x);
//            PosLog.info("y:" + y);
//            drawable.setBounds(0, 0, 50, 50);
//            pluButtonView.setCompoundDrawables(null, drawable, null, null);

        pluButtonView.setCurrentSaleQty(new BigDecimal(2));
    }
}
