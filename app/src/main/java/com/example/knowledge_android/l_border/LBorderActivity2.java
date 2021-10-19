package com.example.knowledge_android.l_border;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

/**
 * 思想：
 * ①设置下方组件的marginTop为 -2dp，目的是让上组件的下边框和下组件的上边框重叠。
 * ②设置上组件选中时，只有左上右边框，然后上组件的下边框就会盖着下组建的一部分，以达到下组建部分边框不可见问题。
 *
 * 注意：
 * ①下方组件的marginTop尽可能的大，才能保证下方组件的上边框，和上边组件的下边框重叠。（这个值多大，需要你自己去慢慢调整才知道）
 * ②边框的bottom尽可能的大，才能保证上方组件，覆盖到和下方组件重叠的区域。（这个值多大，需要你自己去慢慢调整才知道）
 * <layer-list>
 *  <item android:bottom="-10dp">
 */

public class LBorderActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layer_list_test);
    }
}
