package com.example.knowledge_android.android_fragment.static_load;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //加载布局得到View对象并返回

        //创建一个视图对象，设置数据并返回
        TextView textView = new TextView(getActivity());
        textView.setText("fragment1111");
        textView.setBackgroundColor(Color.GREEN);

        return textView;
        //return super.onCreateView(inflater, container, savedInstanceState); 此方法源码返回时null，所以这里需要我们自己写的
    }
}
