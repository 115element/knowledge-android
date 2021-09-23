package com.example.knowledge_android.hyicoupon;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> views;
    private List<String> titles;

    public MyViewPagerAdapter() {
    }

    public MyViewPagerAdapter(List<View> views, List<String> titles) {
        this.views = views;
        this.titles = titles;
    }

    // 设置ViewPager标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    // Viewpage有几个界面
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 添加一个界面
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);
        return view;
    }

    // 删除一个界面
    // container:ViewPager
    // post：当前界面的位置
    // object:view对象
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = views.get(position);
        ViewPager viewPager = (ViewPager) container;
        viewPager.removeView(view);
    }
}

