package com.example.knowledge_android.viewpager.viewpager_fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.knowledge_android.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 三个ViewPager可以左右滑动
 */

public class MainActivity72 extends AppCompatActivity {

    private List<Fragment> frags;    //存放每一个Fragment
    private List<String> titles;     //存放指示器的标题
    private ViewPager viewPager;
    private MagicIndicator indicator;//指示器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_5);

        initData();
        initPager();
        initIndicator();

        //给ViewPager添加监听器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                indicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                indicator.onPageScrollStateChanged(state);
            }
        });

        //绑定ViewPager和指示器
        ViewPagerHelper.bind(indicator, viewPager);
    }


    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.frag_pager);
        DiyFragmentAdapter adapter = new DiyFragmentAdapter(getSupportFragmentManager(), frags);
        viewPager.setAdapter(adapter);
    }

    private void initIndicator() {
        indicator = (MagicIndicator) findViewById(R.id.top_indicator);
        CommonNavigator navigator = new CommonNavigator(this);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return frags.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setText(titles.get(i));
                titleView.setTextSize(18);
                titleView.setNormalColor(Color.LTGRAY);
                titleView.setSelectedColor(Color.WHITE);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("TAG","点击TAB");
                        viewPager.setCurrentItem(i); //跳转到特定的页面
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator pagerIndicator = new BezierPagerIndicator(context);
                pagerIndicator.setColors(Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.GREEN);
                return pagerIndicator;
            }
        });
        indicator.setNavigator(navigator);
    }

    private void initData() {
        frags = new ArrayList<>();
        frags.add(MyFragment.newInstance(R.layout.viewpager_fragment1));
        frags.add(MyFragment.newInstance(R.layout.viewpager_fragment2));
        frags.add(MyFragment.newInstance(R.layout.viewpager_fragment3));
        titles = new ArrayList<>();
        titles.add("First");
        titles.add("Second");
        titles.add("Third");
    }
}
