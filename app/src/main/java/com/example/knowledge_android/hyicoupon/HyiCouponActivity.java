package com.example.knowledge_android.hyicoupon;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;

public class HyiCouponActivity extends AppCompatActivity {

    ListView couponView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hyi_coupon);
        couponView = findViewById(R.id.hyi_coupon);
        List<HyiCoupon> hyiCouponList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HyiCoupon hyiCoupon = new HyiCoupon();
            hyiCoupon.setId(i);
            if (i % 2 == 0) {
                hyiCoupon.setRecommend(true);
            } else {
                hyiCoupon.setRecommend(false);
            }
            hyiCouponList.add(hyiCoupon);
        }
        CouponListView.CouponListViewAdapter<HyiCoupon> objectCouponListViewAdapter = new CouponListView.CouponListViewAdapter<>(hyiCouponList);
        couponView.setAdapter(objectCouponListViewAdapter);
    }
}
