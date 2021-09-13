package com.example.knowledge_android.activity2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview2.adapter.NoFoldAdapter;
import com.example.knowledge_android.recyclerview2.bean.MapBean;

import java.util.ArrayList;
import java.util.List;

public class NoFoldActivity extends AppCompatActivity {

    private List<MapBean> mapBeanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoFoldAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //0.加载布局文件
        setContentView(R.layout.nofold_layout);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //1.准备数据
        mapBeanList = (List<MapBean>) bundle.getSerializable("mapBeanList");
        if (mapBeanList.size() == 0) {//判断是否为空
            Toast.makeText(this, "没有数据！",
                    Toast.LENGTH_SHORT).show();
        }

        //2.得到布局文件中的RecyclerView组件
        recyclerView = findViewById(R.id.nofold_recyclerview);

        //3.创建布局管理器，并设置给该组件
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //4.创建适配器
        adapter = new NoFoldAdapter(mapBeanList);

        //5.设置RecycleView的适配器
        recyclerView.setAdapter(adapter);
    }

}

