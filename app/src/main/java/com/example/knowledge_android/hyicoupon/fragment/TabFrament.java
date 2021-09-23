package com.example.knowledge_android.hyicoupon.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TabFrament extends Fragment {
    private Context context;
    private String content;

    public TabFrament() {
    }

    public TabFrament(Context contexts, String content) {
        this.context = contexts;
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(context);
        textView.setText(content);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}