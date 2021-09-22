package com.example.knowledge_android.hyicoupon;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.knowledge_android.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponListView<T> extends ListView {


    public CouponListView(Context context) {
        super(context);
    }

    public CouponListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static class CouponListViewAdapter<T> extends BaseAdapter {
        List<T> model;

        public List<T> getModel() {
            return model;
        }

        public void setModel(List<T> model) {
            this.model = model;
        }

        public CouponListViewAdapter(List<T> model) {
            this.model = model;
        }

        @Override
        public int getCount() {
            return model.size();
        }

        @Override
        public Object getItem(int position) {
            return model.get(position);
        }

        @Override
        public long getItemId(int position) {
            HyiCoupon t = (HyiCoupon) model.get(position);
            return t.getId();
        }

        private final Map<Integer, View> map = new HashMap<>();

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HyiCoupon itemData = (HyiCoupon) get(position);
            CouponViewHolder couponViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_show, parent, false);
                couponViewHolder = new CouponViewHolder();
                ImageView coupon_recommend;
                if (itemData.isRecommend()) {
                    coupon_recommend = convertView.findViewById(R.id.coupon_recommend);
                    coupon_recommend.setVisibility(View.VISIBLE);
                } else {
                    coupon_recommend = convertView.findViewById(R.id.coupon_recommend);
                    coupon_recommend.setVisibility(View.INVISIBLE);
                }
                TextView couponTitle = convertView.findViewById(R.id.coupon_title);
                TextView coupon_surplus = convertView.findViewById(R.id.coupon_surplus);
                TextView coupon_startEndTime = convertView.findViewById(R.id.coupon_startEndTime);
                TextView coupon_amount = convertView.findViewById(R.id.coupon_amount);
                TextView coupon_desc = convertView.findViewById(R.id.coupon_desc);
                ImageView coupon_plus = convertView.findViewById(R.id.coupon_plus);
                TextView coupon_num = convertView.findViewById(R.id.coupon_num);
                ImageView coupon_minus = convertView.findViewById(R.id.coupon_minus);
                ImageView coupon_select = convertView.findViewById(R.id.coupon_select);
                couponViewHolder.position = position;
                couponViewHolder.couponTitle = couponTitle;
                couponViewHolder.coupon_recommend = coupon_recommend;
                couponViewHolder.coupon_surplus = coupon_surplus;
                couponViewHolder.coupon_startEndTime = coupon_startEndTime;
                couponViewHolder.coupon_amount = coupon_amount;
                couponViewHolder.coupon_desc = coupon_desc;
                couponViewHolder.coupon_plus = coupon_plus;
                couponViewHolder.coupon_num = coupon_num;
                couponViewHolder.coupon_minus = coupon_minus;
                couponViewHolder.coupon_select = coupon_select;
                convertView.setTag(couponViewHolder);
                map.put(position, convertView);
            }
            if (map.get(position) == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_show, parent, false);
                couponViewHolder = new CouponViewHolder();
                ImageView coupon_recommend;
                if (itemData.isRecommend()) {
                    coupon_recommend = convertView.findViewById(R.id.coupon_recommend);
                    coupon_recommend.setVisibility(View.VISIBLE);
                } else {
                    coupon_recommend = convertView.findViewById(R.id.coupon_recommend);
                    coupon_recommend.setVisibility(View.INVISIBLE);
                }
                TextView couponTitle = convertView.findViewById(R.id.coupon_title);
                TextView coupon_surplus = convertView.findViewById(R.id.coupon_surplus);
                TextView coupon_startEndTime = convertView.findViewById(R.id.coupon_startEndTime);
                TextView coupon_amount = convertView.findViewById(R.id.coupon_amount);
                TextView coupon_desc = convertView.findViewById(R.id.coupon_desc);
                ImageView coupon_plus = convertView.findViewById(R.id.coupon_plus);
                TextView coupon_num = convertView.findViewById(R.id.coupon_num);
                ImageView coupon_minus = convertView.findViewById(R.id.coupon_minus);
                ImageView coupon_select = convertView.findViewById(R.id.coupon_select);
                couponViewHolder.position = position;
                couponViewHolder.couponTitle = couponTitle;
                couponViewHolder.coupon_recommend = coupon_recommend;
                couponViewHolder.coupon_surplus = coupon_surplus;
                couponViewHolder.coupon_startEndTime = coupon_startEndTime;
                couponViewHolder.coupon_amount = coupon_amount;
                couponViewHolder.coupon_desc = coupon_desc;
                couponViewHolder.coupon_plus = coupon_plus;
                couponViewHolder.coupon_num = coupon_num;
                couponViewHolder.coupon_minus = coupon_minus;
                couponViewHolder.coupon_select = coupon_select;
                convertView.setTag(couponViewHolder);
                map.put(position, convertView);
            }
            View convertViewSave = map.get(position);
            convertViewSave.setBackgroundResource(R.drawable.c);
            couponViewHolder = (CouponViewHolder) convertViewSave.getTag();
            CouponViewHolder finalCouponViewHolder = couponViewHolder;
            if (!finalCouponViewHolder.coupon_select.hasOnClickListeners()) {
                finalCouponViewHolder.coupon_select.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("TA", "点击优惠券");
                        HyiCoupon t = (HyiCoupon) get(position);
                        Log.i("TAG", "点击的交易数据" + t.toString());
                        if (t.isSelected()) {
                            t.setSelected(false);
                            finalCouponViewHolder.coupon_select.setImageResource(R.mipmap.unselect);
                        } else {
                            t.setSelected(true);
                            finalCouponViewHolder.coupon_select.setImageResource(R.mipmap.select);
                        }
                        finalCouponViewHolder.coupon_select.postInvalidate();
                    }
                });
            } else {
                Log.i("TAG", "已经配置了舰艇");
            }
            finalCouponViewHolder.coupon_plus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence text = finalCouponViewHolder.coupon_num.getText();
                    int i = Integer.parseInt((String) text);
                    i += 1;
                    int couponTotalAmount = itemData.getCouponTotalAmount();
                    if (i >= couponTotalAmount) {
                        i = couponTotalAmount;
                    }
                    finalCouponViewHolder.coupon_num.setText(String.valueOf(i));
                    itemData.setCouponUsed(i);
                }
            });

            finalCouponViewHolder.coupon_minus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence text = finalCouponViewHolder.coupon_num.getText();
                    int i = Integer.parseInt((String) text);
                    i -= 1;
                    if (i == 0) {
                        i = 1;
                    }
                    finalCouponViewHolder.coupon_num.setText(String.valueOf(i));
                    itemData.setCouponUsed(i);
                }
            });
            return convertViewSave;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        public T get(int position) {
            return model.get(position);
        }
    }

    public static class CouponViewHolder {
        private int position;
        private ImageView coupon_recommend;
        private TextView couponTitle;
        private TextView coupon_surplus;
        private TextView coupon_startEndTime;
        private TextView coupon_amount;
        private TextView coupon_desc;
        private ImageView coupon_plus;
        private TextView coupon_num;
        private ImageView coupon_minus;
        private ImageView coupon_select;
    }
}
