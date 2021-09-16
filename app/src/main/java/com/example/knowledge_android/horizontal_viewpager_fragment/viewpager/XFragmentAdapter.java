package com.example.knowledge_android.horizontal_viewpager_fragment.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * FragmentPagerAdapter:
 * FragmentPagerAdapter会将每一个生成的Fragment保存在内存中，所以只适用于页面较少的情况。如果页面比较多，会导致占用较大内存，影响用户体验。
 *
 * FragmentStatePagerAdapter:
 * 只保留当前页面，当页面离开视线后，就会将fragment消除，释放资源，在需要fragment需要显示的时候，生成新的页面，好处是不会占用大量内存
 *
 * FragmentPagerAdapter  === 适用于较少的页面，占用内存
 *
 * FragmentStatePagerAdapter === 适用于不确定数量，较多数量的页面时使用，大量页面时不会占用大量内存
 *
 */

public class XFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> datas;

    public List<Fragment> getDatas() {
        return datas;
    }

    public void setDatas(List<Fragment> datas) {
        this.datas = datas;
    }

    public XFragmentAdapter(FragmentManager fm, List<Fragment> list) {
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
