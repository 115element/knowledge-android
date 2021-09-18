package com.example.linkage_recyclerview.adapter.viewholder;


import android.view.View;

import androidx.annotation.NonNull;

import com.example.linkage_recyclerview.contract.ILinkagePrimaryAdapterConfig;


public class LinkagePrimaryViewHolder extends BaseViewHolder {

    public View mGroupTitle;
    public View mLayout;
    private ILinkagePrimaryAdapterConfig mConfig;

    public LinkagePrimaryViewHolder(@NonNull View itemView, ILinkagePrimaryAdapterConfig config) {
        super(itemView);
        mConfig = config;
        mGroupTitle = itemView.findViewById(mConfig.getGroupTitleViewId());
        //need bind root layout by users, because rootLayout may not viewGroup, which can not getChild(0).
        mLayout = itemView.findViewById(mConfig.getRootViewId());
    }
}
