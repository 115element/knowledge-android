package com.example.knowledge_android.activity7.viewpager_view8;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * PageAdapter——PageView的适配器
 *
 * 适配器这个东东想必大家都不莫生，在ListView中也有适配器，listView通过重写GetView（）函数来获取当前要加载的Item。而PageAdapter不太相同，毕竟PageAdapter是单个VIew的合集。
 *
 * getCount():返回要滑动的VIew的个数
 * destroyItem()：从当前container中删除指定位置（position）的View;
 * instantiateItem()：做了两件事，第一：将当前视图添加到container中，第二：返回当前View
 */
public class Diy2PagerAdapter extends PagerAdapter {

    private List<View> viewList; //view数组

    public Diy2PagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    /**
     * 楼上表达的太惨不忍睹，问题不回答就带着你绕。理清楚了再回答，不要强答。百度里还一堆复制他的回答，真tm！！ 本身不是复杂的问题 特地注册帐号回答
     *
     * 以下正文：
     * 前提概念：
     * ViewPager里面对每个页面的管理是key-value形式的，也就是说每个page都有个对应的id（id是object类型），需要对page操作的时候都是通过id来完成的
     *
     * 首先看这个函数
     * public Object instantiateItem(ViewGroup container, int position)；
     * 这是pageAdapter里的函数，功能就是往PageView里添加自己需要的page。同时注意它还有个返回值object，这就是那个id。
     *
     * 最后:
     * public abstract boolean isViewFromObject (View view, Object object)
     * 这个函数就是用来告诉框架，这个view的id是不是这个object。
     * 谷歌官方推荐把view当id用，所以常规的instantiateItem()函数的返回值是你自己定义的view，而isViewFromObject()的返回值是view == object。
     *
     * ps：感觉这个机制应该是历史遗留问题，属于改bug改出来的机制。否则官方不会推荐这种把view当id的做法。
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(viewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
}
