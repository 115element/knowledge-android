package com.example.knowledge_android.horizontal_viewpager_fragment.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

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
