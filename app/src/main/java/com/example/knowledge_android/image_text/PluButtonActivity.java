package com.example.knowledge_android.image_text;

import android.graphics.Color;
import android.os.Bundle;
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

        pluButtonView.setBackgroundColor(Color.RED);
        pluButtonView.setText("图片描述");
        pluButtonView.setSingleLine(false);
        pluButtonView.setMinLines(1);
        pluButtonView.setMaxLines(4);
        pluButtonView.setPadding(2, 2, 2, 2);
        //TODO 格式可能跟PC版不一樣
        pluButtonView.setGravity(Gravity.LEFT);

        //TODO 一句代码值千金
        //pluButtonView.setBackgroundResource(R.drawable.top);
        pluButtonView.setBitmap(R.drawable.plu);

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
