package com.example.knowledge_android.secondary_list.listener_interface;

import android.view.View;

import com.example.knowledge_android.secondary_list.bean.Province;

public interface OnItemViewClickListener {
    void setOnViewClickListener(View view, int position, Province.City city);
}
