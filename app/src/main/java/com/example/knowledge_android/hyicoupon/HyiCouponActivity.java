package com.example.knowledge_android.hyicoupon;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.knowledge_android.R;
import com.example.knowledge_android.comparator.astuetz.PagerSlidingTabStrip;
import com.example.knowledge_android.hyicoupon.fragment.TabFrament;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HyiCouponActivity extends AppCompatActivity {

    ListView couponView;
    ViewPager viewPager;
    PagerTabStrip pagerTabStrip;

    TabLayout tabLayout;

    private List<Fragment> fragments = new ArrayList<>();
    private static List<String> tabs = new ArrayList<>();

    private List<View> viewList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hyi_coupon2);

        viewPager = findViewById(R.id.viewpager);

        initData();
//        pagerTabStrip = findViewById(R.id.pagerTitleStrip);
//        pagerTabStrip.setPadding(0,0,0,0);
//        pagerTabStrip.setTextSpacing(1);

        viewList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            CouponListView<Object> couponView = new CouponListView<>(getBaseContext());
            List<HyiCoupon> hyiCouponList = new ArrayList<>();
            for (int x = 0; x < 10; x++) {
                HyiCoupon hyiCoupon = new HyiCoupon();
                hyiCoupon.setId(i);
                if (x % 2 == 0) {
                    hyiCoupon.setRecommend(true);
                } else {
                    hyiCoupon.setRecommend(false);
                }
                hyiCouponList.add(hyiCoupon);
            }
            CouponListView.CouponListViewAdapter<HyiCoupon> adapter1 = new CouponListView.CouponListViewAdapter<>(hyiCouponList);
            couponView.setAdapter(adapter1);
            viewList.add(couponView);
        }

//
//        List<String> titles = new ArrayList<String>();
//        titles.add("全部");
//        titles.add("可用");
//        titles.add("不可用");


//        MyViewPagerAdapter adapter = new MyViewPagerAdapter(viewList, titles);
//        viewPager.setAdapter(adapter);


//        couponView = findViewById(R.id.hyi_coupon);
//        List<HyiCoupon> hyiCouponList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            HyiCoupon hyiCoupon = new HyiCoupon();
//            hyiCoupon.setId(i);
//            if (i % 2 == 0) {
//                hyiCoupon.setRecommend(true);
//            } else {
//                hyiCoupon.setRecommend(false);
//            }
//            hyiCouponList.add(hyiCoupon);
//        }
//        CouponListView.CouponListViewAdapter<HyiCoupon> objectCouponListViewAdapter = new CouponListView.CouponListViewAdapter<>(hyiCouponList);
//        couponView.setAdapter(objectCouponListViewAdapter);
    }


    private void initData() {
        tabs.add("新消息");
        tabs.add("朋友圈");
        tabs.add("公众号");
        fragments.add(new TabFrament(this, tabs.get(0)));
        fragments.add(new TabFrament(this, tabs.get(1)));
        fragments.add(new TabFrament(this, tabs.get(2)));

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        //关联ViewPager和TabLayout
        tabLayout.setupWithViewPager(viewPager);
        //设置分割线
        LinearLayout linear = (LinearLayout) tabLayout.getChildAt(0);
        linear.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //设置分割图片
        //linear.setDividerDrawable(ContextCompat.getDrawable(this,R.drawable.f));
        //设置分割线间隔
        linear.setDividerPadding(dip2px(15));
    }

    //像素单位转换
    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }


    //ViewPager-可以选择使用Fragment,也可以使用View
    public class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //显示标签上的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }


    //ViewPager-可以选择使用View
    public static class Diy1PagerAdapter extends PagerAdapter {

        private final List<View> viewList; //view数组

        public Diy1PagerAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
