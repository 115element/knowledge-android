package com.example.knowledge_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.knowledge_android.recyclerview1.bean.Fruit;
import com.example.knowledge_android.recyclerview1.bean.FruitAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.初始化数据
        initFruit();

        //2.找到RecyclerView视图
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_1);

        //3.新建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //4.创建适配器
        FruitAdapter fruitAdapter = new FruitAdapter(fruitList);

        //5.设置适配器
        recyclerView.setAdapter(fruitAdapter);
    }


    public void initFruit(){
        for (int i = 0; i < 5; i++) {
            Fruit orange = new Fruit("orange",R.drawable.add);
            fruitList.add(orange);
            Fruit waterMelon = new Fruit("waterMelon",R.drawable.add);
            fruitList.add(waterMelon);
            Fruit apple = new Fruit("apple",R.drawable.add);
            fruitList.add(apple);
        }
    }
}