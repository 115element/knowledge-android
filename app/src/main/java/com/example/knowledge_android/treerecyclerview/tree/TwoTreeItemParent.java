package com.example.knowledge_android.treerecyclerview.tree;

import com.example.knowledge_android.R;
import com.example.knowledge_android.treerecyclerview.adpater.TreeViewHolder;
import com.example.knowledge_android.treerecyclerview.base.BaseItem;
import com.example.knowledge_android.treerecyclerview.tree.bean.TranDetail;
import com.example.knowledge_android.treerecyclerview.view.TreeItemGroup;

import java.util.List;



/**
 * @author Alex
 * @since 2021/09/12
 *
 * 第二层
 */
public class TwoTreeItemParent extends TreeItemGroup<TranDetail> {

    @Override
    public List<? extends BaseItem> initChildsList(TranDetail data) {
        return null;
    }

    @Override
    public int initLayoutId() {
        return R.layout.tree_child;
    }


    @Override
    public void onBindViewHolder(TreeViewHolder holder) {
        holder.setText(R.id.pluId, getData().getPluId());
        holder.setText(R.id.pluName, getData().getPluName());
        holder.setText(R.id.fixName1, getData().getFixName1());
        holder.setText(R.id.choice, getData().getChoice());
        holder.setText(R.id.option, getData().getOption());
        holder.setText(R.id.fixName2, getData().getFixName2());
        holder.setText(R.id.quantity, getData().getQuantity());
        holder.setText(R.id.fixName3, getData().getFixName3());
        holder.setText(R.id.amount, getData().getAmount());
    }

    @Override
    public boolean isCanChangeExpand() {
        return false;
    }

    @Override
    public boolean canExpandOrCollapse() {
        return false;
    }
}
