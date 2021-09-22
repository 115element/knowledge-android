package com.example.knowledge_android.expandable_list_view;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.expandable_list_view.adapter.MyExpandableListViewAdapter;
import com.example.knowledge_android.expandable_list_view.bean.ChildData;
import com.example.knowledge_android.expandable_list_view.bean.GroupData;

import java.util.ArrayList;
import java.util.List;


/*
 * 头像是通过Glide获取，gradle导入Glide以及Glide的简单实用。
 *
 * compile 'com.github.bumptech.glide:glide:3.7.0'
 *
 * Glide.with(context)
 * .load(childData.getUrl())
 * .placeholder(R.mipmap.ic_launcher)
 * .error(R.mipmap.ic_launcher)
 * .into(holder.img);
 *
 * placeholder：图片加载到之前显示的图片
 * error：图片加载失败显示的图片
 *
 */

public class ExpandableListViewActivity2 extends AppCompatActivity {

    private ExpandableListView listView;
    private MyExpandableListViewAdapter adapter;
    private List<GroupData> groupList;
    private List<List<ChildData>> childList;

    private String[] url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        init();
        loadData();
    }

    private void init() {
        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        adapter = new MyExpandableListViewAdapter(this, groupList, childList);
        listView.setAdapter(adapter);

        //重写OnGroupClickListener，实现当展开时，ExpandableListView不自动滚动
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    //第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition, false);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });
    }

    private void loadData() {
        url = new String[]{
                "http://cdn.duitang.com/uploads/item/201506/07/20150607125903_vFWC5.png",
                "http://upload.qqbody.com/ns/20160915/202359954jalrg3mqoei.jpg",
                "http://tupian.qqjay.com/tou3/2016/0726/8529f425cf23fd5afaa376c166b58e29.jpg",
                "http://cdn.duitang.com/uploads/item/201607/13/20160713094718_Xe3Tc.png",
                "http://img3.imgtn.bdimg.com/it/u=1808104956,526590423&fm=11&gp=0.jpg",
                "http://tupian.qqjay.com/tou3/2016/0725/5d6272a4acd7e21b2391aff92f765018.jpg"
        };

        List<String> group = new ArrayList<>();
        group.add("我的设备");
        group.add("我的好友");
        group.add("初中同学");
        group.add("高中同学");
        group.add("大学同学");

        for (int i = 0; i < group.size(); i++) {
            GroupData gd = new GroupData(group.get(i), (i + 2) + "/" + (2 * i + 2));
            groupList.add(gd);
        }

        for (int i = 0; i < group.size(); i++) {
            List<ChildData> list = new ArrayList<>();
            for (int j = 0; j < 2 * i + 2; j++) {
                ChildData cd = null;
                if (i == 0) {
                    cd = new ChildData("null", "我的手机", "上次登录");
                    list.add(cd);
                    cd = new ChildData("null", "发现新设备", "玩转只能信设备，发现新生活");
                    list.add(cd);
                    break;
                } else {
                    cd = new ChildData(url[j % url.length], "张三" + j, "你好！！！");
                    list.add(cd);
                }
            }
            childList.add(list);
        }
    }
}
