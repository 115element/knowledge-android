package com.example.knowledge_android.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arasthel.swissknife.SwissKnife;
import com.arasthel.swissknife.annotations.InjectView;
import com.example.knowledge_android.R;
import com.makeramen.roundedimageview.RoundedImageView;


public class PluButtonImageView extends LinearLayout {

    @InjectView(R.id.plu_image_view) //这个注解使用Groovy实现，所以这里注入会报错
    RoundedImageView pluImageView;

    @InjectView(R.id.plu_name)//这个注解使用Groovy实现，所以这里注入会报错
    TextView pluName;


    public PluButtonImageView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);

        View container = inflate(context, R.layout.plu_button_image_view, this);
        SwissKnife.inject(this, container);

        pluImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","click >>>");
            }
        });

        pluName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","click >>>");
            }
        });
    }
}
