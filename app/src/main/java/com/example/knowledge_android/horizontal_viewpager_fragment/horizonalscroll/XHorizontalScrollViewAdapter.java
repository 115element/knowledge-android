package com.example.knowledge_android.horizontal_viewpager_fragment.horizonalscroll;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.knowledge_android.R;
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
import java.util.List;
import java.util.Random;

public class XHorizontalScrollViewAdapter {

    private Context mContext;
    private LayoutInflater mInflate;
    private List<Integer> mDatas;
    private ViewPager viewPager;
    private MagicIndicator indicator;

    public XHorizontalScrollViewAdapter(Context context, List<Integer> mDatas, ViewPager viewPager,MagicIndicator indicator) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.viewPager = viewPager;
        this.indicator = indicator;
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
            viewHolder.btn = (Button) convertView.findViewById(R.id.id_x_horizontal_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (XViewHolder) convertView.getTag();
        }
        //设置背景图片
        //viewHolder.mImg.setBackgroundResource(mDatas.get(position));
        viewHolder.btn.setBackgroundResource(R.drawable.plu_big_button_bg);
        //viewHolder.mText.setText("some info ");
        if (!viewHolder.btn.hasOnClickListeners()) {
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    XFragmentAdapter adapter = (XFragmentAdapter) viewPager.getAdapter();
                    frags = new ArrayList<>();
                    XFragment xFragment1 = XFragment.newInstance(R.layout.viewpager_fragment1);
                    XFragment xFragment2 = XFragment.newInstance(R.layout.viewpager_fragment2);
                    XFragment xFragment3 = XFragment.newInstance(R.layout.viewpager_fragment3);
                    frags.add(xFragment1);
                    frags.add(xFragment2);
                    frags.add(xFragment3);

                    changeIndicator();

                    adapter.setDatas(frags);
                    adapter.notifyDataSetChanged();

                    //恢复ViewPager的item到第一位，因为数据重新加载了。
                    viewPager.setCurrentItem(0);

                    Log.i("TAG", "点击商品大类");
                }
            });
        }

        return convertView;
    }

    private static class XViewHolder {
        Button btn;
    }

    List<Fragment> frags;
    private List<String> titles = new ArrayList<>();     //存放指示器的标题
    private void changeIndicator() {
        Random random = new Random();
        int i = random.nextInt(1000);
        titles.clear();
        titles.add("First"+i);
        titles.add("Second"+i);
        titles.add("Third"+i);
        CommonNavigator navigator = new CommonNavigator(mContext);
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
                        Log.i("TAG", "点击TAB:"+i);
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

        //绑定ViewPager和指示器
        ViewPagerHelper.bind(indicator, viewPager);
    }

}
