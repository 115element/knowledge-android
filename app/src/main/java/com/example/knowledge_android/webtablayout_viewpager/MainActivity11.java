package com.example.knowledge_android.webtablayout_viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Gravity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.webtablayout_viewpager.webtablayout.WeTabLayout;
import com.example.knowledge_android.webtablayout_viewpager.fragment.CeshiFragment;


public class MainActivity11 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_11);
        final WeTabLayout tabLayoutOne = findViewById(R.id.dil_tablayout);
        final WeTabLayout tabLayoutTwo = findViewById(R.id.dil_tablayout2);
        final WeTabLayout tabLayoutThree = findViewById(R.id.dil_tablayout3);



        ViewPager viewPager = findViewById(R.id.viewpager);
        final String[] titlesOne = {"这个很长电影啊", "NBA", "小知识"};
        final String[] titlesTwo = {"移动", "四个字的", "小灵通"};
        final String[] titlesThree = {"手机", "电脑", "车子"};


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return CeshiFragment.newInstance();
            }

            @Override
            public int getCount() {
                return titlesTwo.length;
            }
        });

        tabLayoutOne.setTabContainerGravity(Gravity.CENTER);
        tabLayoutOne.setIndicatorResId(R.mipmap.ic_vip_logo);
        tabLayoutOne.attachToViewPager(viewPager,titlesOne);

        tabLayoutTwo.setTabContainerGravity(Gravity.CENTER);
        tabLayoutTwo.setIndicatorResId(R.mipmap.ic_vip_logo);
        tabLayoutTwo.attachToViewPager(viewPager,titlesTwo);

        tabLayoutThree.setTabContainerGravity(Gravity.CENTER);
        tabLayoutThree.setIndicatorResId(R.mipmap.ic_vip_logo);
        tabLayoutThree.attachToViewPager(viewPager,titlesThree);

    }
}
