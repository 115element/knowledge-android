package com.example.knowledge_android.qbadgeview.choice_option;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Badge视图
 */

public class MainActivity7 extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.plu_info_recycler_view);

        recyclerView = findViewById(R.id.recyclerView2);
        //FlexboxLayoutManager实现水平过界换行,就是增强版的LinearLayout
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getBaseContext());
        //设置自动换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //设置主轴的方向
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        recyclerView.setLayoutManager(flexboxLayoutManager);


        //RecyclerView嵌套RecyclerView点击被拦截
        //解决方案:
        //上层： recyclerView.setLayoutFrozen(true);
        //下层可以接收到点击事件
        //recyclerView.setLayoutFrozen(true);//冻结
        //recyclerView.suppressLayout(true);//压制


        List<DataBean> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            DataBean bean = new DataBean();
            bean.setViewType(i);
            bean.setName("规格值" + i);
            bean.setPrice("+¥" + i + "元");
            list.add(bean);
        }
        ChoiceOptionAdapter choiceOptionAdapter = new ChoiceOptionAdapter(list);
        recyclerView.setAdapter(choiceOptionAdapter);
    }
}
