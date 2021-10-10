package com.example.knowledge_android.activity4;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview4.adapter.FoAdapter;
import com.example.knowledge_android.recyclerview4.bean.BaseBean;
import com.example.knowledge_android.recyclerview4.bean.MapBeanChild;
import com.example.knowledge_android.recyclerview4.bean.MapBeanParent;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现父子组件不同布局，可以使用不同实例数据封装。
 */

public class MainActivity4 extends AppCompatActivity {

    private final List<BaseBean> baseBeans = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fo_layout);
        initMaps();

        recyclerView = (RecyclerView) findViewById(R.id.fo_recyclerview);

//        //瀑布流布局
//        StaggeredGridLayoutManager staggeredGridLayoutManager =
//                new StaggeredGridLayoutManager(4,
//                        StaggeredGridLayoutManager.VERTICAL);
//        //网格布局
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
//        //设置动画
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        //可用于设置分割线、或者实现类似通讯录里边头部悬停那种效果
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.top = 10;
//                if (view.getLayoutParams() instanceof GridLayoutManager.LayoutParams) {
//                    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
//                    int spanIndex = layoutParams.getSpanIndex();//在一行中所在的角标，第几列
//                    if (spanIndex != ((GridLayoutManager) parent.getLayoutManager()).getSpanCount() - 1) {
//                        outRect.right = 10;
//                    }
//                }
//            }
//        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FoAdapter(baseBeans);
        recyclerView.setAdapter(adapter);
    }

    private void initMaps() {
        String[] parent = {"动物", "植物"};
        String[][] child = {{"熊猫", "大象", "老虎"},
                {"百合", "向日葵", "樱花"}};
        for (int i = 0; i < parent.length; i++) {
            MapBeanParent parentBean = new MapBeanParent(parent[i], MapBeanParent.TYPE_PARENT);

            List<MapBeanChild> childList = new ArrayList<>();//保存二级标题
            for (int j = 0; j < child[i].length; j++) {
                MapBeanChild childBean = new MapBeanChild(child[i][j], MapBeanParent.TYPE_CHILD);
                childList.add(childBean);
            }
            parentBean.setChildList(childList);
            baseBeans.add(parentBean);
        }
    }
}
