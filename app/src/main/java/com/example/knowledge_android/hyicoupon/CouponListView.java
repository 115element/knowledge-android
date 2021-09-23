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
            return position;
        }


        /**
         * @param position      当前item的位置，对应List数据
         * @param convertView   初始化时，它为空，如果之前已经创建可以显示一满屏幕的itemView,后边滑动时就会复用之前的itemView,那么这个参数就是划出屏幕的那个itemView.
         * @param parent        ListView的父布局组件
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HyiCoupon itemData = (HyiCoupon) get(position);
            CouponViewHolder couponViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_show2, parent, false);
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
            } else {
                couponViewHolder = (CouponViewHolder) convertView.getTag();
            }
            convertView.setBackgroundResource(R.mipmap.coupon_bg);


            //TODO 注意(这是一个重点知识-使用时一定会遇到)：由于ListView会复用之前的View，那么就会导致之前的View的状态消失。
            //TODO 所以我们使用数据，来保存或者还原之前View的状态。这是最好的办法。
            //TODO B哥也是这么做的，在它的商品明细列表中。
            boolean selected = itemData.isSelected();
            int couponUsed = itemData.getCouponUsed();
            if (selected) {
                couponViewHolder.coupon_select.setImageResource(R.mipmap.coupon_select);
            } else {
                couponViewHolder.coupon_select.setImageResource(R.mipmap.unselect);
            }
            couponViewHolder.coupon_num.setText(String.valueOf(couponUsed));
            ////////////////以上是还原View状态/////////////////////////////////


            CouponViewHolder finalCouponViewHolder = couponViewHolder;
            couponViewHolder.coupon_select.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //监听器中的ItemData是变化的，它指向当前方法的ItemData。因为当前ItemData也随着position变化而变化。
                    Log.i("TAG", "点击的交易数据" + itemData.toString());
                    if (itemData.isSelected()) {
                        itemData.setSelected(false);
                        finalCouponViewHolder.coupon_select.setImageResource(R.mipmap.unselect);
                    } else {
                        itemData.setSelected(true);
                        finalCouponViewHolder.coupon_select.setImageResource(R.mipmap.select);
                    }
                    finalCouponViewHolder.coupon_select.postInvalidate();
                }
            });
            finalCouponViewHolder.coupon_plus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //监听器中的ItemData是变化的，它指向当前方法的ItemData。因为当前ItemData也随着position变化而变化。
                    Log.i("TAG", "+++点击的交易数据" + itemData.toString());
                    int couponUsed1 = itemData.getCouponUsed();
                    couponUsed1 += 1;
                    int couponTotalAmount = itemData.getCouponTotalAmount();
                    if (couponUsed1 >= couponTotalAmount) {
                        couponUsed1 = couponTotalAmount;
                    }
                    finalCouponViewHolder.coupon_num.setText(String.valueOf(couponUsed1));
                    itemData.setCouponUsed(couponUsed1);
                }
            });
            finalCouponViewHolder.coupon_minus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //监听器中的ItemData是变化的，它指向当前方法的ItemData。因为当前ItemData也随着position变化而变化。
                    Log.i("TAG", "---点击的交易数据" + itemData.toString());
                    int couponUsed1 = itemData.getCouponUsed();
                    couponUsed1 -= 1;
                    if (couponUsed1 == 0) {
                        couponUsed1 = 1;
                    }
                    finalCouponViewHolder.coupon_num.setText(String.valueOf(couponUsed1));
                    itemData.setCouponUsed(couponUsed1);
                }
            });
            return convertView;
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
