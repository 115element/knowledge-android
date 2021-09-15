package com.example.knowledge_android.horizontalscrollview_viewpager_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.knowledge_android.R;


/**
 * Created by Administrator on 2016/7/14.
 */
public class NewsFragment extends Fragment {

    String text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle args = getArguments();
        text = (args != null ? args.getString("text") : "");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(text);
        return view;
    }
}
