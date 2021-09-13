package com.example.knowledge_android.treerecyclerview.view;


import com.example.knowledge_android.treerecyclerview.base.BaseItem;

import java.util.List;



interface TreeParent {
    /**
     * 是否允许展开
     *
     * @return
     */
    boolean canExpandOrCollapse();

    /**
     * 展开后的回调
     */
    void onExpand();

    /**
     * 折叠后的回调
     */
    void onCollapse();

    /**
     * 获取子集
     *
     * @return
     */
    List<? extends BaseItem> getChilds();

}
