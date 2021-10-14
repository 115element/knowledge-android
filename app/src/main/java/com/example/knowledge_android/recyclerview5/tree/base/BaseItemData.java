package com.example.knowledge_android.recyclerview5.tree.base;


public abstract class BaseItemData {

    protected int viewItemType;  //标记视图类型，可以通过实体中的该属性，确定视图类型

    public void setViewItemType(int viewItemType) {
        this.viewItemType = viewItemType;
    }

    public int getViewItemType() {
        return viewItemType;
    }
}
