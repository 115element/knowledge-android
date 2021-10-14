package com.example.knowledge_android.recyclerview5.view;


import com.example.knowledge_android.recyclerview5.tree.base.BaseItem;
import com.example.knowledge_android.recyclerview5.tree.base.BaseItemData;


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
