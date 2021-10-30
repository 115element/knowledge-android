package com.example.knowledge_android.qbadgeview.choice_option;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView的适配器
 */
public class ChoiceOptionAdapter extends RecyclerView.Adapter<ChoiceOptionAdapter.ChoiceOptionViewHolder> {

    List<DataBean> list = new ArrayList<>();

    public ChoiceOptionAdapter(List<DataBean> list) {
        this.list = list;
    }


    //////////////////////////////The Following Is ViewHolder//////////////////////////////////////

    /**
     * 适配器的ViewHolder
     */
    public static class ChoiceOptionViewHolder extends RecyclerView.ViewHolder {

        private TextView pluView;
        private TextView pluRightTopView;

        public ChoiceOptionViewHolder(@NonNull View itemView) {
            super(itemView);

            //处理布局中的组件
            pluView = itemView.findViewById(R.id.plu_view);
            pluRightTopView = itemView.findViewById(R.id.plu_right_top_view);

            itemView.setOnClickListener(view -> Log.i("TAG", "onClick: 点击Item"));
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////The Following Is SubItem create////////////////////////////////
    // onCreateViewHolder(ViewGroup parent, int viewType)：用于初始化Item布局
    // 参数一：ViewGroup parent，是指RecycleView的布局
    // 参数二：int viewType，是指Item的属性，该属性是在public int getItemViewType(int position)中进行设置获取的。
    // public int getItemViewType(int position)：获取每个Item中的viewType;
    // RecycleView调用流程：getItemViewType先于onCreateViewHolder。
    @NonNull
    @Override
    public ChoiceOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "===③创建RecyclerView的适配器的ViewHolder==="+"viewType="+viewType);
        //看源码,这里的parent就是Recyclerview,所以不会为null.可以通过它拿到context
        View view = View.inflate(parent.getContext(), R.layout.choice_option_recycler_view_item, null);
        return new ChoiceOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceOptionViewHolder holder, int position) {
        Log.i("TAG", "===④绑定ViewHolder===" + "Position:" + position);
        DataBean bean = list.get(position);

        View itemView = holder.itemView;
        holder.pluView.setText(bean.getName());
        holder.pluRightTopView.setText(bean.getPrice());
    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "===①获取RecyclerView的子项数量==="+list.size());
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        DataBean bean = list.get(position);
        Log.i("TAG", "===②获取视图类型==="+bean.getViewType());
        return bean.getViewType();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


}
