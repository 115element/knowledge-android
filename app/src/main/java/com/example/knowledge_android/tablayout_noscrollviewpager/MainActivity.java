package com.example.knowledge_android.tablayout_noscrollviewpager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.knowledge_android.R;
import com.example.knowledge_android.noscrollviewpager.NoScrollViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final List<String> tabs = new ArrayList<>();
    static {
        tabs.add("新消息");
        tabs.add("朋友圈");
    }
    private static final List<View> viewList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_improve);
        TabLayout tabLayout = findViewById(R.id.tab_im);
        NoScrollViewPager viewPager = findViewById(R.id.view_pager_im);
        viewPager.setScroll(false);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        TextView textView1 = new TextView(getBaseContext());
        textView1.setText("123");
        TextView textView2 = new TextView(getBaseContext());
        textView2.setText("456");
        viewList.add(textView1);
        viewList.add(textView2);


        viewPager.setAdapter(new TabAdapter());
        tabLayout.setupWithViewPager(viewPager);


//        TextView textView = new TextView(getBaseContext());
//        textView.setBackgroundColor(Color.RED);
//        viewList.add(textView);
//        viewList.add(textView);
//        tabLayout.setTabsFromPagerAdapter(new TabAdapter()); 该方法已废弃，不生效了。
    }


    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            CharSequence text = tab.getText();
            System.out.println("点击："+text);
            //TODO 这里加载View的数据
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    //ViewPager-可以选择使用Fragment,也可以使用View
    public class TabAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object; ////这行代码很重要，它用于判断你当前要显示的页面
        }

        //显示标签上的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
