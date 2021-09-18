package com.example.linkage_recyclerview.contract;


import android.content.Context;
import android.view.View;

import com.example.linkage_recyclerview.adapter.viewholder.LinkagePrimaryViewHolder;


public interface ILinkagePrimaryAdapterConfig {

    /**
     * setContext
     *
     * @param context context
     */
    void setContext(Context context);

    /**
     * get layout res id
     *
     * @return layout res id
     */
    int getLayoutId();

    /**
     * get textView id of layout
     *
     * @return textView id of layout
     */
    int getGroupTitleViewId();

    /**
     * get rootView id of layout
     *
     * @return rootView id of layout
     */
    int getRootViewId();

    /**
     * achieve the onBindViewHolder logic on outside
     * <p>
     * Note: Do not setOnClickListener in onBindViewHolder,
     * instead, you can deal with item click in method 'ILinkagePrimaryAdapterConfig.onItemSelected()'
     * or 'LinkageRecyclerView.OnPrimaryItemClickListener.onItemClick()'
     *
     * @param holder   LinkagePrimaryViewHolder
     * @param title    title of this position
     * @param selected selected of this position
     * @param position holder.getAdapterPosition()
     */
    void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title, int position);

    /**
     * on primary item clicked
     *
     * @param view     itemView
     * @param title    title of primary item
     * @param position position
     */
    void onItemClick(View view, String title, int position);
}
