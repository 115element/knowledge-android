package com.example.knowledge_android.horizontal_viewpager_fragment.horizonalscroll;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.viewpager.widget.ViewPager;

import com.example.knowledge_android.R;
import com.example.knowledge_android.other.utils.RandomColors;

import java.util.ArrayList;
import java.util.List;

public class XHorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflate;
    private List<Integer> mDatas;
    private List<ViewPager> viewPagers = new ArrayList<>();

    public XHorizontalScrollViewAdapter(Context context, List<Integer> mDatas, List<ViewPager> viewPagers) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.viewPagers = viewPagers;
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
        XViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new XViewHolder();
            convertView = mInflate.inflate(R.layout.activity_main_10_horizontal, parent, false);
            viewHolder.mImg = (Button) convertView.findViewById(R.id.id_x_horizontal_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (XViewHolder) convertView.getTag();
        }
        //设置背景图片
        //viewHolder.mImg.setBackgroundResource(mDatas.get(position));
        viewHolder.mImg.setBackgroundResource(R.drawable.plu_big_button_bg);
        //viewHolder.mText.setText("some info ");
        if (!viewHolder.mImg.hasOnClickListeners()) {
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < viewPagers.size(); i++) {
                        ViewPager viewPager = viewPagers.get(i);
                        int childCount = viewPager.getChildCount();
                        for (int c = 0; c < childCount; c++) {
                            View childAt = viewPager.getChildAt(c);
                            childAt.setBackgroundColor(Color.parseColor(RandomColors.getRandColorCode()));
                        }

                        Log.i("TAG","(ViewGroup)ViewPager总共几个子元素"+childCount+"");
                        viewPager.setBackgroundColor(Color.GRAY);
                        viewPager.postInvalidate();
                    }
                    Log.i("TAG", "点击商品大类");
                }
            });
        }

        return convertView;
    }

    private static class XViewHolder {
        Button mImg;
    }

}
