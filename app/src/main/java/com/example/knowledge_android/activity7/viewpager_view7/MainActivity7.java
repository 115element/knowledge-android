package com.example.knowledge_android.activity7.viewpager_view7;

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
import com.example.knowledge_android.activity7.transformer.ScaleViewPagerTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager可以左右滑动
 */

public class MainActivity7 extends AppCompatActivity {

    private View view1, view2, view3;

    private ViewPager viewPager; //对应ViewPager

    private List<View> viewList; //view数组

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_4);

        viewPager = findViewById(R.id.viewpager);

        LayoutInflater inflater = getLayoutInflater();
        /**
         * 本节继续带来的是Android系统服务中的LayoutInflater(布局服务)，说到布局，大家第一时间 可能想起的是写完一个布局的xml，然后调用Activity的setContentView()加载布局，然后把他显示 到屏幕上是吧~其实这个底层走的还是这个LayoutInflater，用的Android内置的Pull解析器来解析 布局。一般在Android动态加载布局或者添加控件用得较多.
         *
         * Layout是什么鬼？
         * 答：一个用于加载布局的系统服务，就是实例化与Layout XML文件对应的View对象，不能直接使用， 需要通过getLayoutInflater( )方法或getSystemService( )方法来获得与当前Context绑定的 LayoutInflater实例!
         *
         * public View inflate (int resource, ViewGroup root, boolean attachToRoot) 该方法的三个参数依次为:
         * ①要加载的布局对应的资源id
         * ②为该布局的外部再嵌套一层父布局，如果不需要的话，写null就可以了!
         * ③是否为加载的布局文件的最外层套一层root布局，不设置该参数的话， 如果root不为null的话，则默认为true 如果root为null的话，attachToRoot就没有作用了! root不为null，attachToRoot为true的话，会在加载的布局文件最外层嵌套一层root布局; 为false的话，则root失去作用! 简单理解就是：是否为加载的布局添加一个root的外层容器~!
         */
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

        /**
         * ViewPage使用，最重要的是setOnPageChangeListener，传入一个ViewPager.SimpleOnPageChangeListener对象或ViewPager.OnPageChangeListener。
         * 它们有三个方法，下面做介绍：
         * 重要方法：viewPager.setCurrentItem(int position); //跳转到特定的页面
         * 注：ViewPager有两个操作，一个是用手指滑动翻页，一个是直接setCurrentItem（一般用于点击上面的tab直接setCurrentItem）。下面的方法介绍也会针对这两种情况分别介绍。
         *
         * ViewPager.OnPageChangeListener或ViewPager.SimpleOnPageChangeListener：
         *
         * 1.onPageScrolled(int position, float positionOffset, int positionOffsetPixels):
         * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。
         * 其中三个参数的含义分别为：
         *
         * position :当前页面，及你点击滑动的页面
         * positionOffset:当前页面偏移的百分比
         * positionOffsetPixels:当前页面偏移的像素位置
         *
         * 详解：这个方法会在屏幕滚动过程中不断被调用。
         *
         * 有三个参数，第一个position，这个参数要特别注意一下。当用手指滑动时，如果手指按在页面上不动，position和当前页面index是一致的；
         * 如果手指向左拖动（相应页面向右翻动），这时候position大部分时间和当前页面是一致的，只有翻页成功的情况下最后一次调用才会变为目标页面；
         * 如果手指向右拖动（相应页面向左翻动），这时候position大部分时间和目标页面是一致的，只有翻页不成功的情况下最后一次调用才会变为原页面。
         * 当直接设置setCurrentItem翻页时，如果是相邻的情况（比如现在是第二个页面，跳到第一或者第三个页面），如果页面向右翻动，大部分时间是和当前页面是一致的，只有最后才变成目标页面；
         * 如果向左翻动，position和目标页面是一致的。这和用手指拖动页面翻动是基本一致的。
         * 如果不是相邻的情况，比如我从第一个页面跳到第三个页面，position先是0，然后逐步变成1，然后逐步变成2；我从第三个页面跳到第一个页面，position先是1，然后逐步变成0，并没有出现为2的情况。
         * positionOffset是当前页面滑动比例，如果页面向右翻动，这个值不断变大，最后在趋近1的情况后突变为0。如果页面向左翻动，这个值不断变小，最后变为0。
         * positionOffsetPixels是当前页面滑动像素，变化情况和positionOffset一致。
         * （参见官网：http://docs.eoeandroid.com)
         *
         *
         * 2.onPageSelected(int arg0) ：  此方法是页面跳转完后得到调用，arg0是你当前选中的页面的Position（位置编号）。
         *
         * 详解：onPageSelected(int position)：这个方法有一个参数position，代表哪个页面被选中。
         * 当用手指滑动翻页的时候，如果翻动成功了（滑动的距离够长），手指抬起来就会立即执行这个方法，position就是当前滑动到的页面。
         * 如果直接setCurrentItem翻页，那position就和setCurrentItem的参数一致，这种情况在onPageScrolled执行方法前就会立即执行。
         *
         *
         *
         * 3.onPageScrollStateChanged(int arg0)  ，此方法是在状态改变的时候调用，其中arg0这个参数
         *
         * 有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
         *
         * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0），
         *
         * 详解：这个方法在手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP) 。
         *
         * 当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1，手指抬起时，如果发生了滑动（即使很小），这个值会变为2，然后最后变为0 。总共执行这个方法三次。一种特殊情况是手指按下去以后一点滑动也没有发生，这个时候只会调用这个方法两次，state值分别是1,0 。
         *
         * 当setCurrentItem翻页时，会执行这个方法两次，state值分别为2 , 0 。
         *
         *
         * 三个方法的执行顺序为：用手指拖动翻页时，
         * 最先执行一遍onPageScrollStateChanged（1），然后不断执行onPageScrolled，
         * 放手指的时候，直接立即执行一次onPageScrollStateChanged（2），然后立即执行一次onPageSelected，然后再不断执行onPageScrolled，最后执行一次onPageScrollStateChanged（0）。
         * 其它的情况由这个可以推出来，不再赘述。
         *
         */

        //设置自定义切换动画
        viewPager.setPageTransformer(true, new ScaleViewPagerTransformer());

        //自定义适配器
        Diy1PagerAdapter diy1PagerAdapter = new Diy1PagerAdapter(viewList);

        viewPager.setAdapter(diy1PagerAdapter);
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
        List<View> pages=new ArrayList<>();
        Field[] fields=R.drawable.class.getDeclaredFields();
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
