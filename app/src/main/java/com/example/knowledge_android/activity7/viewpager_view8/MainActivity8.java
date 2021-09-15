package com.example.knowledge_android.activity7.viewpager_view8;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.knowledge_android.R;
import com.example.knowledge_android.activity7.ImageUtils;
import com.example.knowledge_android.activity7.navigator.ScaleCircleNavigator;
import com.example.knowledge_android.activity7.transformer.ScaleViewPagerTransformer;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity8 extends AppCompatActivity {

    private View view1, view2, view3;

    private ViewPager viewPager; //对应ViewPager

    private List<View> viewList; //view数组

    private MagicIndicator indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_6);

        viewPager = findViewById(R.id.frag_pager6);

        LayoutInflater inflater = getLayoutInflater();

        view1 = inflater.inflate(R.layout.viewpager_layout1, null);
        view2 = inflater.inflate(R.layout.viewpager_layout2, null);
        view3 = inflater.inflate(R.layout.viewpager_layout3, null);

        //viewList = new ArrayList<View>(); //将要分页显示的view装入数组
        //viewList.add(view1);
        //viewList.add(view2);
        //viewList.add(view3);


        viewList = getPages();
        //TODO viewList = getPages();  使用该方法可以获得有倒影的图片

        //ViewPage监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("当前页面，既你滑动的页面", position + "");
                Log.i("当前页面偏移的百分比", positionOffset + "");
                Log.i("当前页面偏移的像素位置", positionOffsetPixels + "");
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("此方法是页面跳转完后得到调用", "position是你当前选中的页面的Position（位置编号）:" + position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
                Log.i("状态:", state + "");
            }
        });

        //设置自定义切换动画
        viewPager.setPageTransformer(true, new ScaleViewPagerTransformer());

        //自定义适配器
        Diy2PagerAdapter diy2PagerAdapter = new Diy2PagerAdapter(viewList);

        viewPager.setAdapter(diy2PagerAdapter);

        initIndicator();
        ViewPagerHelper.bind(indicator, viewPager);
    }


    private void initIndicator() {
        indicator = (MagicIndicator) findViewById(R.id.bottom_indicator);
        ScaleCircleNavigator navigator = new ScaleCircleNavigator(this);
        navigator.setCircleCount(viewList.size());
        navigator.setNormalCircleColor(Color.DKGRAY);
        navigator.setSelectedCircleColor(Color.CYAN);
        navigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                viewPager.setCurrentItem(index);
            }
        });
        indicator.setNavigator(navigator);
    }


    //将图片变为有倒影的图片
    private List<View> getPages() {
        List<View> pages = new ArrayList<>();
        Field[] fields = R.drawable.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().startsWith("page")) {
                    ImageView view = new ImageView(this);
                    view.setImageBitmap(ImageUtils.getReverseBitmapById(this, field.getInt(null), 0.5f));
                    pages.add(view);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return pages;
    }


    //获取普通图片资源 drawable目录下所有page开头的图片
    private List<View> getPages2() {
        List<View> pages = new ArrayList<>();
        Field[] fields = R.drawable.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().startsWith("page")) {
                    ImageView view = new ImageView(this);
                    view.setImageResource(field.getInt(null));
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    pages.add(view);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
