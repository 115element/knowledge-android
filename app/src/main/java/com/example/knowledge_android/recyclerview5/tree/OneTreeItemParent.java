package com.example.knowledge_android.recyclerview5.tree;


import android.graphics.Color;
import android.widget.Button;

import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview5.adpater.TreeViewHolder;
import com.example.knowledge_android.recyclerview5.factory.ItemFactory;
import com.example.knowledge_android.recyclerview5.tree.bean.TranHead;
import com.example.knowledge_android.recyclerview5.view.TreeItem;
import com.example.knowledge_android.recyclerview5.view.TreeItemGroup;

import java.util.List;
import java.util.Objects;


/**
 * @author Alex
 * @since 2021/09/12
 * <p>
 * 第一层
 */
public class OneTreeItemParent extends TreeItemGroup<TranHead> {

    @Override
    public List<? extends TreeItem> initChildsList(TranHead data) {
        return ItemFactory.createTreeItemList(data.getTranDetails(), TwoTreeItemParent.class, this);
    }

    @Override
    public int initLayoutId() {
        return R.layout.tree_parent5;
    }


    @Override
    public void onBindViewHolder(TreeViewHolder holder) {
        holder.setText(R.id.foodCode, data.getFoodCode());
        holder.setText(R.id.channel, data.getChannel());
        holder.setText(R.id.date, data.getDate());
        holder.setText(R.id.phone, data.getPhone());
        holder.setText(R.id.amount, data.getAmount());
        holder.setText(R.id.status, data.getStatus());

        Button refund = holder.itemView.findViewById(R.id.status);
        if (refund != null) {
            String buttonText = refund.getText().toString();
            if (Objects.equals(buttonText, "已退货")) {
                refund.setTextColor(Color.GRAY);
                refund.setBackgroundColor(Color.parseColor("#D7DFF0"));
            }
            if (Objects.equals(buttonText, "退货中")) {
                refund.setTextColor(Color.YELLOW);
                refund.setBackgroundColor(Color.parseColor("#D7DFF0"));
            }
            if (Objects.equals(buttonText, "退货")) {
                refund.setTextColor(Color.WHITE);
                refund.setBackgroundResource(R.drawable.circular_shape);
            }
            //Log.i("TAG","按钮名称:" + refund.getText().toString());
        }
    }

    @Override
    public boolean canExpandOrCollapse() {
        return false;
    }
}
