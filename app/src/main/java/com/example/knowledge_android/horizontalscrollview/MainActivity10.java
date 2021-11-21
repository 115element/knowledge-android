package com.example.knowledge_android.horizontalscrollview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//自定义的HorizontalScrollView使用方法

public class MainActivity10 extends AppCompatActivity {

    private MyHorizontalScrollView mHorizontalScrollView;
    private MyHorizontalScrollViewAdapter mAdapter;
    private ImageView mImage;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.eeee, R.drawable.f, R.drawable.g));

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature();的取值
        // 1.DEFAULT_FEATURES：系统默认状态，一般不需要指定
        // 2.FEATURE_CONTEXT_MENU：启用ContextMenu，默认该项已启用，一般无需指定
        // 3.FEATURE_CUSTOM_TITLE：自定义标题。当需要自定义标题时必须指定。如：标题是一个按钮时
        // 4.FEATURE_INDETERMINATE_PROGRESS：不确定的进度
        // 5.FEATURE_LEFT_ICON：标题栏左侧的图标
        // 6.FEATURE_NO_TITLE：无标题
        // 7.FEATURE_OPTIONS_PANEL：启用“选项面板”功能，默认已启用。
        // 8.FEATURE_PROGRESS：进度指示器功能
        // 9.FEATURE_RIGHT_ICON:标题栏右侧的图标
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_8);
        mImage = (ImageView) findViewById(R.id.id_content);

        //①获取自定义HorizontalScrollView组件
        mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
        //②创建自定义适配器
        mAdapter = new MyHorizontalScrollViewAdapter(this, mDatas);
        //③自定义的监听器，没有用哦，不会触发
        mHorizontalScrollView.setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
                Log.i("DIY", "当前图片变化");
                mImage.setImageResource(mDatas.get(position));
                viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //点击监听
        mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i("DIY", "点击了");
                mImage.setImageResource(mDatas.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //滚动监听
        mHorizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.i("TAG", "滚动监听");
                Log.i("A", i + "");
                Log.i("B", i1 + "");
                Log.i("C", i2 + "");
                Log.i("D", i3 + "");
            }
        });

        mHorizontalScrollView.initDatas(mAdapter);
    }
}


