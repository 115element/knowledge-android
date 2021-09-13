package com.example.knowledge_android.activity2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview2.bean.MapBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private List<MapBean> mapBeanList = new ArrayList<>();//初始化保存标题，然后通过intent传输到各个activity中

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        initMaps();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("", "非折叠");
                Intent intent = new Intent(MainActivity2.this, NoFoldActivity.class);
                onClicks(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("", "折叠列表");
                Intent intent = new Intent(MainActivity2.this, FoldActivity.class);
                onClicks(intent);
            }
        });
    }

    public void onClicks(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mapBeanList", (Serializable) mapBeanList);//把list强转成Serializable类型
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void initMaps() {
        String[] parent = {"动物", "植物", "玩具"};
        String[][] child = {
                {"熊猫", "大象", "老虎"},
                {"百合", "向日葵", "樱花"},
                {"手机", "电脑", "天空"}
        };
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