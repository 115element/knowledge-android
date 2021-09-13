package com.example.knowledge_android.treerecyclerview.view;


import com.example.knowledge_android.treerecyclerview.base.BaseItem;
import com.example.knowledge_android.treerecyclerview.base.BaseItemData;




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
