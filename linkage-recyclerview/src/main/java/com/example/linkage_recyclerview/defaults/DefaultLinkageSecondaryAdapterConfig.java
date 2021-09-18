package com.example.linkage_recyclerview.defaults;

import android.content.Context;
import android.widget.TextView;

import com.example.linkage_recyclerview.R;
import com.example.linkage_recyclerview.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.example.linkage_recyclerview.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.example.linkage_recyclerview.adapter.viewholder.LinkageSecondaryViewHolder;
import com.example.linkage_recyclerview.bean.BaseGroupedItem;
import com.example.linkage_recyclerview.bean.DefaultGroupedItem;
import com.example.linkage_recyclerview.contract.ILinkageSecondaryAdapterConfig;


public class DefaultLinkageSecondaryAdapterConfig implements ILinkageSecondaryAdapterConfig<DefaultGroupedItem.ItemInfo> {

    private Context mContext;
    private OnSecondaryItemBindListener mItemBindListener;
    private OnSecondaryHeaderBindListener mHeaderBindListener;
    private OnSecondaryFooterBindListener mFooterBindListener;
    private static final int SPAN_COUNT = 3;

    public void setItemBindListener(OnSecondaryItemBindListener itemBindListener,
                                    OnSecondaryHeaderBindListener headerBindListener,
                                    OnSecondaryFooterBindListener footerBindListener) {
        mItemBindListener = itemBindListener;
        mHeaderBindListener = headerBindListener;
        mFooterBindListener = footerBindListener;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getGridLayoutId() {
        return R.layout.default_adapter_linkage_secondary_grid;
    }

    @Override
    public int getLinearLayoutId() {
        return R.layout.default_adapter_linkage_secondary_linear;
    }

    @Override
    public int getHeaderLayoutId() {
        return R.layout.default_adapter_linkage_secondary_header;
    }

    @Override
    public int getFooterLayoutId() {
        return R.layout.default_adapter_linkage_secondary_footer;
    }

    @Override
    public int getHeaderTextViewId() {
        return R.id.secondary_header;
    }

    @Override
    public int getSpanCountOfGridMode() {
        return SPAN_COUNT;
    }

    @Override
    public void onBindViewHolder(LinkageSecondaryViewHolder holder,
                                 BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position) {

        ((TextView) holder.getView(R.id.level_2_title)).setText(item.info.getTitle());
        ((TextView) holder.getView(R.id.level_2_content)).setText(item.info.getContent());

        if (mItemBindListener != null) {
            mItemBindListener.onBindViewHolder(holder, item, position);
        }
    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position) {

        ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);

        if (mHeaderBindListener != null) {
            mHeaderBindListener.onBindHeaderViewHolder(holder, item, position);
        }
    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position) {
        ((TextView) holder.getView(R.id.tv_secondary_footer)).setText(mContext.getString(R.string.the_end));

        if (mFooterBindListener != null) {
            mFooterBindListener.onBindFooterViewHolder(holder, item, position);
        }
    }

    public interface OnSecondaryItemBindListener {
        void onBindViewHolder(LinkageSecondaryViewHolder secondaryHolder,
                              BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position);
    }

    public interface OnSecondaryHeaderBindListener {
        void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder headerHolder,
                                    BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position);
    }

    public interface OnSecondaryFooterBindListener {
        void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder footerHolder,
                                    BaseGroupedItem<DefaultGroupedItem.ItemInfo> item, int position);
    }
}
