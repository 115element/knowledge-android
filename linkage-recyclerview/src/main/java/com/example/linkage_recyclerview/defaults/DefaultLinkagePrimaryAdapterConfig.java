package com.example.linkage_recyclerview.defaults;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.linkage_recyclerview.R;
import com.example.linkage_recyclerview.adapter.viewholder.LinkagePrimaryViewHolder;
import com.example.linkage_recyclerview.contract.ILinkagePrimaryAdapterConfig;


public class DefaultLinkagePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;
    private OnPrimaryItemBindListener mListener;
    private OnPrimaryItemClickListner mClickListner;

    public void setListener(OnPrimaryItemBindListener listener,
                            OnPrimaryItemClickListner clickListner) {
        mListener = listener;
        mClickListner = clickListner;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.default_adapter_linkage_primary;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group;
    }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title, int position) {
        TextView tvTitle = ((TextView) holder.mGroupTitle);
        tvTitle.setText(title);

        tvTitle.setBackgroundColor(mContext.getResources().getColor(selected ? R.color.colorPurple : R.color.colorWhite));
        tvTitle.setTextColor(ContextCompat.getColor(mContext, selected ? R.color.colorWhite : R.color.colorGray));
        tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        tvTitle.setFocusable(selected);
        tvTitle.setFocusableInTouchMode(selected);
        tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);

        if (mListener != null) {
            mListener.onBindViewHolder(holder, title, position);
        }
    }

    @Override
    public void onItemClick(View view, String title, int position) {
        if (mClickListner != null) {
            mClickListner.onItemClick(view, title, position);
        }
    }

    public interface OnPrimaryItemClickListner {
        void onItemClick(View view, String title, int position);
    }

    public interface OnPrimaryItemBindListener {
        /**
         * Note: Please do not override rootView click listener in here, because of linkage selection rely on it.
         *
         * @param primaryHolder primaryHolder
         * @param title         groupTitle
         * @param position      position
         */
        void onBindViewHolder(LinkagePrimaryViewHolder primaryHolder, String title, int position);
    }
}
