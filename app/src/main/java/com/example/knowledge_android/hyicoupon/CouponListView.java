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

import java.util.List;

public class CouponListView<T> extends ListView {

    private Context context;

    public CouponListView(Context context) {
        super(context);
        this.context = context;
    }

    public CouponListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HyiCoupon itemData = (HyiCoupon) model.get(position);
            CouponViewHolder couponViewHolder = null;
            convertView = null; //拒绝ListView复用
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
            }
            couponViewHolder = (CouponViewHolder) convertView.getTag();

            convertView.setBackgroundResource(R.drawable.c);
            final CouponViewHolder finalCouponViewHolder = couponViewHolder;
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TA","点击优惠券");
                    int position1 = finalCouponViewHolder.position;
                    Log.i("TAG",position1+"");
                    HyiCoupon t = (HyiCoupon) get(position1);
                    if (t.isSelected()) {
                        t.setSelected(false);
                        finalCouponViewHolder.coupon_select.setImageResource(R.mipmap.unselect);
                    } else {
                        t.setSelected(true);
                        finalCouponViewHolder.coupon_select.setImageResource(R.mipmap.select);
                    }
                }
            });


            ImageView coupon_recommend = couponViewHolder.coupon_recommend;
            TextView couponTitle = couponViewHolder.couponTitle;
            TextView coupon_surplus = couponViewHolder.coupon_surplus;
            TextView coupon_startEndTime = couponViewHolder.coupon_startEndTime;
            TextView coupon_amount = couponViewHolder.coupon_amount;
            TextView coupon_desc = couponViewHolder.coupon_desc;
            ImageView coupon_plus = couponViewHolder.coupon_plus;
            TextView coupon_num = couponViewHolder.coupon_num;
            ImageView coupon_minus = couponViewHolder.coupon_minus;
            ImageView coupon_select = couponViewHolder.coupon_select;
            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        public T get(int position){
            return model.get(position);
        }


    }

    public static class CouponViewHolder {
        private int position;
        ImageView coupon_recommend;
        TextView couponTitle;
        TextView coupon_surplus;
        TextView coupon_startEndTime;
        TextView coupon_amount;
        TextView coupon_desc;
        ImageView coupon_plus;
        TextView coupon_num;
        ImageView coupon_minus;
        ImageView coupon_select;
    }
}
