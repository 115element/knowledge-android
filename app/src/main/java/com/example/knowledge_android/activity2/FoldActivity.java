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


/*
 *
 * 用户滑动屏幕切换视图时，上一个视图会回收利用，RecyclerView所做的就是回收再利用，循环往复。
 *
 * ViewHolder
 * ViewHolder的主要任务：容纳View视图。
 *
 * Adapter
 * Adapter从模型层获取数据，然后提供给RecyclerView显示，是沟通的桥梁。Adapter主要的任务是：创建ViewHolder和将模型层的数据绑定到ViewHolder上。
 *
 * 首先，调用Adapter.getItemCount()方法，RecyclerView询问数组列表中包含多少个待展示的视图。
 * 接着，RecyclerView调用Adapter.onCreateViewHolder(ViewGroup, int)创建ViewHolder。
 * 最后，RecyclerView会传入ViewHolder及其位置，调用onBindViewHolder(ViewHolder, int)方法。Adapter会找到目标位置的数据并将其绑定到ViewHolder的视图上。
 * 需要注意的是相对于onBindViewHolder()，onCreateViewHolder()方法调用并不频繁。一旦有了够用的ViewHolder，RecyclerView就会停止调用onCreateViewHolder()方法。随后，它会回收利用旧的ViewHolder以节约时间和内存。
 *
 *
 * 1.LayoutManager
 * RecyclerView不会亲自摆放屏幕上的列表项，摆放列表项的任务被委托给了LayoutManager。除了在屏幕上摆放列表项，LayoutManager还负责定义屏幕滚动行为。
 * 除了一些Android系统内置版实现，LayoutManager还有很多第三方库实现版本。
 *
 *
 *  2.ViewHolder
 * ViewHolder承载的是每一个列表项的视图，所以当使用RecyclerView的时候需要先对ViewHolder进行初始化定义。
 *
 *      private class CrimeHolder extends RecyclerView.ViewHolder {
 *                public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
 *                  super(inflater.inflate(R.layout.list_item_crime, parent, false));
 *            }
 *      }
 * 注意到CrimeHolder构造器方法调用了父类构造器super(View view)----ViewHolder(View view)。因而CrimeHolder实际上引用了已被实例化的list_item_crime布局的视图，且同时这个视图被赋给了父类变量itemView，我们可以在itemView变量处获得该视图。
 *
 *
 * 3.Adapter
 * 当需要显示新创建的ViewHolder或让数据和已创建的ViewHolder关联时，就会用到Adapter。在Adapter中通常需要实现3个方法：
 *
 * onCreateViewHolder(ViewGroup parent, int viewType)
 * 当需要新的ViewHolder来显示列表项时，会调用onCreateViewHolder方法去创建ViewHolder。
 *      public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 *            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
 *            return new CrimeHolder(layoutInflater, parent);
 *      }
 *
 * onBindViewHolder(CrimeHolder holder, int position)
 * 将数据绑定在ViewHolder上。
 *      public void onBindViewHolder(CrimeHolder holder, int position) {
 *              holder.bind(position);
 *      }
 *
 * getItemCount()
 * 返回总共要显示的列表的数量(创建的ViewHolder数量比前者要小得多)。
 *      public int getItemCount() {
 *              return list.size();
 *      }
 *
 */

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

