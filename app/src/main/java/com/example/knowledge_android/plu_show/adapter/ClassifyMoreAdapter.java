package com.example.knowledge_android.plu_show.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;

public class ClassifyMoreAdapter extends RecyclerView.Adapter<ClassifyMoreAdapter.ClassifyMoreAdapterViewHolder> {

  private Context context;
  private String[] text_list;
  private int position = 0;

  public ClassifyMoreAdapter(Context context, String[] text_list) {
    this.context = context;
    this.text_list = text_list;
  }


  public Object getItem(int position) {
    return text_list[position];
  }

  @NonNull
  @Override
  public ClassifyMoreAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_classify_morelist, viewGroup, false);
    ClassifyMoreAdapterViewHolder holder = new ClassifyMoreAdapterViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ClassifyMoreAdapterViewHolder holder, int position) {
    holder.txt.setText(text_list[position]);
    holder.txt.setTextColor(0xFF666666);
  }

  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return text_list.length;
  }


  public void setSelectItem(int position) {
    this.position = position;
  }

  public class ClassifyMoreAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView txt;

    public ClassifyMoreAdapterViewHolder(@NonNull View itemView) {
      super(itemView);
      txt = (TextView) itemView.findViewById(R.id.moreitem_txt);
    }
  }
}
