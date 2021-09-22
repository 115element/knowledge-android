package com.example.knowledge_android.expandable_list_view.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.knowledge_android.R;
import com.example.knowledge_android.expandable_list_view.bean.ChildData;
import com.example.knowledge_android.expandable_list_view.bean.GroupData;

import java.util.List;

public class MyExpandableListViewAdapter implements ExpandableListAdapter {

    private Context context;
    private List<GroupData> groupData;
    private List<List<ChildData>> childData;

    public MyExpandableListViewAdapter(Context context, List<GroupData> groupData, List<List<ChildData>> childData) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    //获取分组个数
    @Override
    public int getGroupCount() {
        int ret = 0;
        if (groupData != null) {
            ret = groupData.size();
        }
        return ret;
    }

    //获取groupPosition分组，子列表数量
    @Override
    public int getChildrenCount(int groupPosition) {
        int ret = 0;
        if (childData != null) {
            ret = childData.get(groupPosition).size();
        }
        return ret;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_expandablelistview, null);
            holder = new GroupViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img_group);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_group_name);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_group_num);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        GroupData groupData = this.groupData.get(groupPosition);
        //是否展开
        if (isExpanded) {
            holder.img.setImageResource(R.drawable.img_bottom);
        } else {
            holder.img.setImageResource(R.drawable.img_right);
        }
        holder.tv_name.setText(groupData.getName());
        holder.tv_num.setText(groupData.getNum());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_expandablelistview, null);
            holder = new ChildViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img_child_head);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_child_name);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_child_content);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        ChildData childData = this.childData.get(groupPosition).get(childPosition);

         //* placeholder：图片加载到之前显示的图片
         //* error：图片加载失败显示的图片
        Glide.with(context)
                .load(childData.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.img);
        holder.tv_name.setText(childData.getName());
        holder.tv_content.setText(childData.getContent());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public static class GroupViewHolder {
        ImageView img;
        TextView tv_name, tv_num;
    }

    public static class ChildViewHolder {
        ImageView img;
        TextView tv_name, tv_content;
    }

}
