package com.example.knowledge_android.recyclerview5.tree;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview5.adpater.TreeViewHolder;
import com.example.knowledge_android.recyclerview5.tree.base.BaseItem;
import com.example.knowledge_android.recyclerview5.tree.bean.TranDetail;
import com.example.knowledge_android.recyclerview5.view.TreeItemGroup;

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
        return R.layout.tree_child_special;
    }


    @Override
    public void onBindViewHolder(TreeViewHolder holder) {

        RecyclerView recyclerView = holder.getView(R.id.details);
        recyclerView.setLayoutManager(new LinearLayoutManager(OneApplication.getInstance().getBaseContext()));
        DetailViewAdapter detailViewAdapter = new DetailViewAdapter();
        recyclerView.setAdapter(detailViewAdapter);
//        holder.setText(R.id.pluId, getData().getPluId());
//        holder.setText(R.id.pluName, getData().getPluName());
//        holder.setText(R.id.fixName1, getData().getFixName1());
//        holder.setText(R.id.choice, getData().getChoice());
//        holder.setText(R.id.option, getData().getOption());
//        holder.setText(R.id.fixName2, getData().getFixName2());
//        holder.setText(R.id.quantity, getData().getQuantity());
//        holder.setText(R.id.fixName3, getData().getFixName3());
//        holder.setText(R.id.amount, getData().getAmount());
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
