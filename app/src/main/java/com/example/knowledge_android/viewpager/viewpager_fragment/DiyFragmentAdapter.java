package com.example.knowledge_android.viewpager.viewpager_fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


/*

ViewPager与Fragment

ViewPager 经常与 Fragment 结合来实现各种页面切换，比如我们开头提到的页面菜单。
由于在 ViewPager 中使用 Fragment 和上面介绍的方式稍有不同，因此我们在这里单独介绍一下。

仔细想想，我们之前的 ViewPager 的页面都是 View，而现在要换成 Fragment，是不是需要替换适配器？
没错，的确如此，不过我们没必要从头写一个适配器，Google为我们提供了两个抽象类：FragmentPagerAdapter 和 FragmentStatePagerAdapter。

不论是继承 FragmentPagerAdapter 还是 FragmentStatePagerAdapter，都需要实现构造器、
int getCount() 和 Fragment getItem(int position) 方法。下面是我们的例子：
 */

/*
FragmentPagerAdapter 中的每一个 Fragment 都将保存在内存之中，因此适用于那些相对静态、数量较少的情况；
如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter。

FragmentStatePagerAdapter 和 FragmentPagerAdapter 一样，继承自 PagerAdapter。
但和 FragmentPagerAdapter 不同的是，正如其类名中的“State”所表明的含义一样，它只保留当前页面。
当页面离开视线后，就会被回收，释放其资源；而在页面需要显示时，将生成新的页面。这样的好处就是当拥有大量的页面时，不会在内存中占用大量的内存。

有了 Fragment 的适配器后，还需要为 ViewPager 设置适配器：

FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager(), frags);
pager.setAdapter(adapter);
 */

public class DiyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> datas;

    public DiyFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        datas = list;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }
}
