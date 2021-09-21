package com.example.knowledge_android.listview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_main);
        ItemListView itemListView = findViewById(R.id.itemListView);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i+"");
        }

        ItemListView.ItemViewListAdapter adapter = new ItemListView.ItemViewListAdapter(list);
        itemListView.setAdapter(adapter);
    }
}
