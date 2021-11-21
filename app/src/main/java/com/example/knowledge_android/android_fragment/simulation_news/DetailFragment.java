package com.example.knowledge_android.android_fragment.simulation_news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


//用来显示详情
public class DetailFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView textView = new TextView(getActivity());
        //取出保存的数据
        Bundle arguments = getArguments();
        String detail = arguments.getString("DETAIL");
        //设置
        textView.setText(detail);
        return textView;
    }
}
