package com.example.knowledge_android.recyclerview3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview3.adpater.TreeRecyclerAdapter;
import com.example.knowledge_android.recyclerview3.factory.ItemFactory;
import com.example.knowledge_android.recyclerview3.tree.OneTreeItemParent;
import com.example.knowledge_android.recyclerview3.tree.bean.TranDetail;
import com.example.knowledge_android.recyclerview3.tree.bean.TranHead;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 使用组件嵌入泓远项目中的方式
 */
public class TreeListView extends LinearLayout {

    TreeRecyclerAdapter<OneTreeItemParent> treeRecyclerAdapter;

    public TreeListView(Context context) {
        super(context);
        inflate(context, R.layout.tree_activity_order, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.order_content);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 6));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 10;
                if (view.getLayoutParams() instanceof GridLayoutManager.LayoutParams) {
                    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                    int spanIndex = layoutParams.getSpanIndex();//在一行中所在的角标，第几列
                    if (spanIndex != ((GridLayoutManager) parent.getLayoutManager()).getSpanCount() - 1) {
                        outRect.right = 10;
                    }
                }
            }
        });

        List<OneTreeItemParent> itemList = ItemFactory.createItemList(getTranHeads(), OneTreeItemParent.class);
        treeRecyclerAdapter = new TreeRecyclerAdapter<>();
        treeRecyclerAdapter.setDatas(itemList);
        recyclerView.setAdapter(treeRecyclerAdapter); //显示列表，不会使用itemView视图缓存
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyDataChanged() {
        List<OneTreeItemParent> itemList = ItemFactory.createItemList(getTranHeads2(), OneTreeItemParent.class);
        treeRecyclerAdapter.setDatas(itemList);
        treeRecyclerAdapter.notifyDataSetChanged(); //使用所有缓存的itemView视图对象
    }


    //Mock Data
    public List<TranHead> getTranHeads() {
        List<TranHead> tranHeads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TranHead tranHead = new TranHead();
            tranHead.setId(i);
            tranHead.setFoodCode("B10005");
            tranHead.setChannel("贝瑞咖啡小程序");
            tranHead.setDate("2021-08-13 16:20:00");
            tranHead.setPhone("13567283718");
            tranHead.setAmount("￥235");
            if (i % 2 == 0) {
                tranHead.setStatus("已退货");
            } else if (i % 3 == 0) {
                tranHead.setStatus("退货中");
            } else {
                tranHead.setStatus("退货");
            }

            List<TranDetail> tranDetails = new ArrayList<>();
            for (int x = 0; x < 3; x++) {
                TranDetail tranDetail = new TranDetail();
                tranDetail.setPluId("1,");
                tranDetail.setPluName("冰美式");
                tranDetail.setFixName1("规格:");
                tranDetail.setChoice("标准");
                tranDetail.setOption("冷");
                tranDetail.setFixName2("数量:");
                tranDetail.setQuantity("1杯");
                tranDetail.setFixName3("金额:");
                tranDetail.setAmount("￥32");
                tranDetails.add(tranDetail);
            }
            tranHead.setTranDetails(tranDetails);
            tranHeads.add(tranHead);
        }
        return tranHeads;
    }

    //这里返回变化后的交易数据
    public List<TranHead> getTranHeads2() {
        Random random = new Random();
        List<TranHead> tranHeads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TranHead tranHead = new TranHead();
            tranHead.setId(i);
            tranHead.setFoodCode("B"+random.nextInt(1000));
            tranHead.setChannel("贝瑞咖啡小程序1");
            tranHead.setDate("2021-08-13 16:20:00");
            tranHead.setPhone("13567283718");
            tranHead.setAmount("￥235");
            if (i % 2 == 0) {
                tranHead.setStatus("已退货");
            } else if (i % 3 == 0) {
                tranHead.setStatus("退货中");
            } else {
                tranHead.setStatus("退货");
            }

            List<TranDetail> tranDetails = new ArrayList<>();
            for (int x = 0; x < 3; x++) {
                TranDetail tranDetail = new TranDetail();
                tranDetail.setPluId("1,");
                tranDetail.setPluName("冰美式");
                tranDetail.setFixName1("规格:");
                tranDetail.setChoice("标准");
                tranDetail.setOption("冷");
                tranDetail.setFixName2("数量:");
                tranDetail.setQuantity("1杯");
                tranDetail.setFixName3("金额:");
                tranDetail.setAmount("￥32");
                tranDetails.add(tranDetail);
            }
            tranHead.setTranDetails(tranDetails);
            tranHeads.add(tranHead);
        }
        return tranHeads;
    }
}
