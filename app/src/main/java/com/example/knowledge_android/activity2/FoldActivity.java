package com.example.knowledge_android.activity2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview2.adapter.FoldAdapter;
import com.example.knowledge_android.recyclerview2.bean.MapBean;

import java.util.ArrayList;
import java.util.List;

public class FoldActivity extends AppCompatActivity {

    private List<MapBean> mapBeanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoldAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fold_layout);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mapBeanList = (List<MapBean>) bundle.getSerializable("mapBeanList");
        //initMaps();

        if (mapBeanList.size() == 0) {
            Toast.makeText(this, "没有对应数据", Toast.LENGTH_SHORT).show();
        }
        recyclerView = (RecyclerView) findViewById(R.id.fold_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FoldAdapter(mapBeanList);
        recyclerView.setAdapter(adapter);
    }


    private void initMaps() {
        String[] parent = {"动物", "植物"};
        String[][] child = {{"熊猫", "大象", "老虎"},
                {"百合", "向日葵", "樱花"}};
        for (int i = 0; i < parent.length; i++) {
            MapBean parentBean = new MapBean(parent[i], MapBean.TYPE_PARENT);
            List<MapBean> childList = new ArrayList<>();//保存二级标题
            for (int j = 0; j < child[i].length; j++) {
                MapBean childBean = new MapBean(child[i][j], MapBean.TYPE_CHILD);
                childList.add(childBean);
            }
            parentBean.setChildList(childList);
            mapBeanList.add(parentBean);
        }
    }
}

