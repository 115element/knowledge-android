package com.example.knowledge_android.linkagelistview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.knowledge_android.R;
import com.example.knowledge_android.databinding.ActivityMainElemeBinding;
import com.example.knowledge_android.linkagelistview.ui.manager.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    private String[] mFragmentTitles;
    private Fragment[] mFragments;

    private ActivityMainElemeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_eleme);

        mBinding.toolbar.setTitle(R.string.app_name);
        setSupportActionBar(mBinding.toolbar);

        mFragmentTitles = getResources().getStringArray(R.array.fragments);
        mFragments = new Fragment[mFragmentTitles.length];

        mBinding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return createFragment2(position);
            }

            @Override
            public int getItemCount() {
                return mFragmentTitles.length;
            }
        });

        new TabLayoutMediator(mBinding.tabs, mBinding.viewPager, (tab, position) -> {
            tab.setText(mFragmentTitles[position].replace("SampleFragment", ""));
        }).attach();
    }

    private Fragment createFragment2(Integer tag) {
        if (mFragments[tag] != null) {
            return mFragments[tag];
        }
        String name = "com.example.knowledge_android.linkagelistview.ui." + mFragmentTitles[tag];
        Fragment fragment = null;
        try {
            fragment = (Fragment) Class.forName(name).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mFragments[tag] = fragment;
        return mFragments[tag];
    }
}
