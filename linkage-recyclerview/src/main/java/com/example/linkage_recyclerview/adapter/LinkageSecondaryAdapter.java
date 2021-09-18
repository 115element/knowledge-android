package com.example.linkage_recyclerview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.linkage_recyclerview.R;
import com.example.linkage_recyclerview.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.example.linkage_recyclerview.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.example.linkage_recyclerview.adapter.viewholder.LinkageSecondaryViewHolder;
import com.example.linkage_recyclerview.bean.BaseGroupedItem;
import com.example.linkage_recyclerview.contract.ILinkageSecondaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

public class LinkageSecondaryAdapter<T extends BaseGroupedItem.ItemInfo> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BaseGroupedItem<T>> mItems;
    private static final int IS_HEADER = 0;
    private static final int IS_LINEAR = 1;
    private static final int IS_GRID = 2;
    private static final int IS_FOOTER = 3;
    private boolean mIsGridMode;

    private ILinkageSecondaryAdapterConfig mConfig;

    public ILinkageSecondaryAdapterConfig getConfig() {
        return mConfig;
    }

    public List<BaseGroupedItem<T>> getItems() {
        return mItems;
    }

    public boolean isGridMode() {
        return mIsGridMode && mConfig.getGridLayoutId() != 0;
    }

    public void setGridMode(boolean isGridMode) {
        mIsGridMode = isGridMode;
    }

    public LinkageSecondaryAdapter(List<BaseGroupedItem<T>> items, ILinkageSecondaryAdapterConfig config) {
        mItems = items;
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mConfig = config;
    }

    public void initData(List<BaseGroupedItem<T>> list) {
        mItems.clear();
        if (list != null) {
            mItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position).isHeader) {
            return IS_HEADER;
        } else if (TextUtils.isEmpty(mItems.get(position).info.getTitle()) &&
                !TextUtils.isEmpty(mItems.get(position).info.getGroup())) {
            return IS_FOOTER;
        } else if (isGridMode()) {
            return IS_GRID;
        } else {
            return IS_LINEAR;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mConfig.setContext(mContext);
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getHeaderLayoutId(), parent, false);
            return new LinkageSecondaryHeaderViewHolder(view);
        } else if (viewType == IS_FOOTER) {
            int footerLayout = mConfig.getFooterLayoutId() == 0
                    ? R.layout.default_adapter_linkage_secondary_footer
                    : mConfig.getFooterLayoutId();
            View view = LayoutInflater.from(mContext).inflate(footerLayout, parent, false);
            return new LinkageSecondaryFooterViewHolder(view);
        } else if (viewType == IS_GRID && mConfig.getGridLayoutId() != 0) {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getGridLayoutId(), parent, false);
            return new LinkageSecondaryViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(mConfig.getLinearLayoutId(), parent, false);
            return new LinkageSecondaryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BaseGroupedItem<T> linkageItem = mItems.get(holder.getAdapterPosition());
        if (getItemViewType(position) == IS_HEADER) {
            LinkageSecondaryHeaderViewHolder headerViewHolder = (LinkageSecondaryHeaderViewHolder) holder;
            mConfig.onBindHeaderViewHolder(headerViewHolder, linkageItem, headerViewHolder.getAdapterPosition());
        } else if (getItemViewType(position) == IS_FOOTER) {
            LinkageSecondaryFooterViewHolder footerViewHolder = (LinkageSecondaryFooterViewHolder) holder;
            mConfig.onBindFooterViewHolder(footerViewHolder, linkageItem, footerViewHolder.getAdapterPosition());
        } else {
            LinkageSecondaryViewHolder secondaryViewHolder = (LinkageSecondaryViewHolder) holder;
            mConfig.onBindViewHolder(secondaryViewHolder, linkageItem, secondaryViewHolder.getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
