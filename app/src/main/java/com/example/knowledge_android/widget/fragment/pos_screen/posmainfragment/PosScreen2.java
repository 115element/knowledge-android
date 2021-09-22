package com.example.knowledge_android.widget.fragment.pos_screen.posmainfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.knowledge_android.R;

public class PosScreen2 extends Fragment implements IPosScreen{


    // onSaveInstanceState()方法解释:
    // 将数据保存到outState对象中, 该对象会在重建activity时传递给onCreate方法
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxmagic, container, false);

        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
