package com.example.knowledge_android.dragreryclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;

import java.util.List;


//RecyclerView的适配器，RecyclerView必须用适配器,适配器用来出来数据，生成视图
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Subject> datas;
    private Context mContext;
    private LayoutInflater mLiLayoutInflater;

    public MyAdapter(List<Subject> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
        this.mLiLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLiLayoutInflater.inflate(R.layout.item_linear, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(datas.get(position).getTitle());
        holder.img.setImageResource(datas.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    //适配器的ViewHolder，Google官方规定，RecyclerView的适配器必须用ViewHolder
    //ViewHolder的作用是：
    //避免重复查找视图，缓存视图，增加运行效率。
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView img;
        LinearLayout ll_item, ll_hidden;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.img = (ImageView) itemView.findViewById(R.id.img);

            this.ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            this.ll_hidden = (LinearLayout) itemView.findViewById(R.id.ll_hidden);
        }
    }

}
