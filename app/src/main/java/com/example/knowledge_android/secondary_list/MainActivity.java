package com.example.knowledge_android.secondary_list;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;
import com.example.knowledge_android.secondary_list.bean.Province;
import com.example.knowledge_android.secondary_list.bean.TestData;
import com.example.knowledge_android.secondary_list.listener_interface.OnItemViewClickListener;
import com.example.knowledge_android.secondary_list.utils.GsonUtils;
import com.example.knowledge_android.secondary_list.view.ExpandableAdapter;
import com.example.knowledge_android.secondary_list.view.FloatItemDecoration;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemViewClickListener {

    RecyclerView recyclerView;
    FloatItemDecoration floatItemDecoration;

    private List<Object> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_second_list);
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //模拟数据
        List<Province> item = GsonUtils.jsonToList(TestData.DATA, Province.class);

        ExpandableAdapter expandableListAdapter = new ExpandableAdapter(item);
        recyclerView.setAdapter(expandableListAdapter);
        floatItemDecoration = new FloatItemDecoration(expandableListAdapter.getObjects(), this);
        recyclerView.addItemDecoration(floatItemDecoration);
        expandableListAdapter.setOnItemViewClickListener(this);
        expandableListAdapter.setOnUIChangeListener(floatItemDecoration);
    }


    @Override
    public void setOnViewClickListener(View view, int position, Province.City city) {
        Toast.makeText(this, "点击了第" + position + "条,当前城市：" + city.getCityName(), Toast.LENGTH_SHORT).show();
    }
}