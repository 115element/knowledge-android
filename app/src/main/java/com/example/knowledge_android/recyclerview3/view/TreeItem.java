package com.example.knowledge_android.recyclerview3.view;


import com.example.knowledge_android.recyclerview3.tree.base.BaseItem;
import com.example.knowledge_android.recyclerview3.tree.base.BaseItemData;


/**
 * TreeRecyclerAdapter的item
 */
public abstract class TreeItem<D extends BaseItemData> extends BaseItem<D> {

    private TreeItemGroup parentItem;

    public void setParentItem(TreeItemGroup parentItem) {
        this.parentItem = parentItem;
    }

    /**
     * 获取当前item的父级
     *
     * @return
     */
    public TreeItemGroup getParentItem() {
        return parentItem;
    }

}
