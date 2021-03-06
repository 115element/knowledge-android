package com.example.knowledge_android.horizontalscrollview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

//原生的HorizontalScrollView使用方法

public class MainActivity9 extends AppCompatActivity {

    private LinearLayout mGallery;
    private int[] mImgIds;
    private LayoutInflater mInflater;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_7);
        mInflater = LayoutInflater.from(this);
        initData();
        initView();
    }


    private void initData() {
        mImgIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
                R.drawable.eeee, R.drawable.f, R.drawable.g
        };
    }

    private void initView() {
        mGallery = (LinearLayout) findViewById(R.id.id_gallery);

        for (int i = 0; i < mImgIds.length; i++) {
            View view = mInflater.inflate(R.layout.activity_index_gallery_item, mGallery, false);
            ImageView img = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
            img.setImageResource(mImgIds[i]);
            TextView txt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
            txt.setText("some info");
            txt.setTextColor(Color.BLACK);
            mGallery.addView(view);
        }
    }
}
