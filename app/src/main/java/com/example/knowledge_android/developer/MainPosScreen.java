package com.example.knowledge_android.developer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.knowledge_android.R;
import com.example.knowledge_android.knowledge.RunTimer;
import com.example.knowledge_android.widget.builder.GViewBuilder;

public class MainPosScreen extends Fragment {


    RunTimer timer = new RunTimer();


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            /*
             * // onSaveInstanceState()方法解释:
             * // 将数据保存到outState对象中, 该对象会在重建activity时传递给onCreate方法
             *
             * java.lang.NullPointerException: Attempt to invoke virtual method 'int java.lang.Integer.intValue()' on a null object reference
             * App Not Response Cause
             *
             * 如果itemList?.selectedLine为空
             * 那么putInt()方法的第二个参数，会被转为int，此时null转为int就会宕机
             * Alex 2019-7-18 修改此方法
             */
            int message = 0;
            outState.putInt("selectedLine", message);
        } catch (Exception ignore) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        timer.start("AndroidPosScreen","Fragment Create View");

        GViewBuilder vb = new GViewBuilder();
        View inflate = inflater.inflate(R.layout.fragment_main_screen, container, false);
        return inflate;
    }
}
