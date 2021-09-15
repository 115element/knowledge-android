package com.example.knowledge_android.horizontalscrollview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.knowledge_android.R;

import java.util.List;

//设置数据适配器
public class MyHorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflate;
    private List<Integer> mDatas;

    public MyHorizontalScrollViewAdapter(Context context, List<Integer> mDatas) {
        this.mContext = context;
        mInflate = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public int getCount() {
        return mDatas.size();
    }

    public Object getItem(int positon) {
        return mDatas.get(positon);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new MyViewHolder();
            convertView = mInflate.inflate(R.layout.activity_index_gallery_item, parent, false);
            viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mText = (TextView) convertView.findViewById(R.id.id_index_gallery_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.mImg.setImageResource(mDatas.get(position));
        viewHolder.mText.setText("some info ");

        return convertView;
    }

    private class MyViewHolder {
        ImageView mImg;
        TextView mText;
    }

}
