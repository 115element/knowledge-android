package com.example.knowledge_android.treerecyclerview.adpater;


import com.example.knowledge_android.treerecyclerview.base.BaseItem;

import java.util.List;


/**
 * @author Alex
 * @since 2021/09/12
 * <p>
 * 数据基类
 */
public interface ItemManager<T extends BaseItem> {
    //增
    void addItem(T item);

    void addItem(int position, T item);

    void addItems(List<T> items);

    void addItems(int position, List<T> items);

    //删
    void removeItem(T item);

    void removeItem(int position);

    void removeItems(List<T> items);


    //改
    void replaceItem(int position, T item);

    //查
    T getItem(int position);

    //通知数据改变
    void notifyDataChanged();

    int getItemPosition(T item);

}