package com.example.knowledge_android.webtablayout_viewpager.webtablayout;

import android.view.View;

public interface WeTabSelectedListener {

    void onTabSelected(View currentTab, int position);

    void onPreTabSelected(View preTab, int prePosition);
}