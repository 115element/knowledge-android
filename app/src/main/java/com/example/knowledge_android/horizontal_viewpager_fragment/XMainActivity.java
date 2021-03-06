package com.example.knowledge_android.horizontal_viewpager_fragment;

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
import com.example.knowledge_android.horizontal_viewpager_fragment.horizonalscroll.XHorizontalScrollView;
import com.example.knowledge_android.horizontal_viewpager_fragment.horizonalscroll.XHorizontalScrollViewAdapter;
import com.example.knowledge_android.horizontal_viewpager_fragment.viewpager.XFragment;
import com.example.knowledge_android.horizontal_viewpager_fragment.viewpager.XFragmentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * HorizontalScrollView + ViewPager + Fragment
 */

public class XMainActivity extends AppCompatActivity {

    private XHorizontalScrollView xHorizontalScrollView;
    private XHorizontalScrollViewAdapter xHorizontalScrollViewAdapter;

    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.eeee, R.drawable.f, R.drawable.g, R.drawable.g,
            R.drawable.g, R.drawable.g));

    private List<Fragment> frags;    //???????????????Fragment
    private ViewPager viewPager;     //
    private MagicIndicator indicator;//?????????
    private List<String> titles;     //????????????????????????

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1.?????????????????? HorizontalScrollView+ViewPager
        setContentView(R.layout.activity_main_10);

        //2.?????????????????????ViewPager
        initFragment();
        initIndicator();
        initViewPager();


        //3.?????????????????????HorizontalScrollView
        xHorizontalScrollView = findViewById(R.id.xHorizontalScrollView);
        //????????????????????????
        xHorizontalScrollViewAdapter = new XHorizontalScrollViewAdapter(this, mDatas, viewPager, indicator);
        xHorizontalScrollView.initDatas(xHorizontalScrollViewAdapter);
    }

    private void initFragment() {
        frags = new ArrayList<>();
        frags.add(XFragment.newInstance(R.layout.viewpager_fragment1));
        frags.add(XFragment.newInstance(R.layout.viewpager_fragment2));
        frags.add(XFragment.newInstance(R.layout.viewpager_fragment3));
        titles = new ArrayList<>();

        Random random = new Random();
        int i = random.nextInt(100);
        titles.add("First" + i);
        titles.add("Second" + i);
        titles.add("Third" + i);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.xViewPager);
        XFragmentAdapter adapter = new XFragmentAdapter(getSupportFragmentManager(), frags);
        viewPager.setAdapter(adapter);

        //???ViewPager???????????????
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

        //??????ViewPager????????????
        ViewPagerHelper.bind(indicator, viewPager);
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
                titleView.setTextSize(20);
                titleView.setNormalColor(Color.LTGRAY);
                titleView.setSelectedColor(Color.WHITE);
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("TAG", "??????TAB");
                        viewPager.setCurrentItem(i); //????????????????????????
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
}
