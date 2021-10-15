package com.example.knowledge_android.recyclerview5.tree;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailViewAdapter extends RecyclerView.Adapter {

    List<String> ss = new ArrayList<>();

    public DetailViewAdapter() {
        ss.clear();
        Random random = new Random();
        int count = random.nextInt(10);
        if (count == 0) {
            count = 1;
        }
        for (int i = 0; i < count; i++) {
            ss.add("1");
        }
//        ss.add("2");
//        ss.add("3");
//        ss.add("4");
//        ss.add("5");
//        ss.add("5");
//        ss.add("5");
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder{

        TextView textView1,textView2,textView3,textView4,textView5,textView6;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.plu_name);
            textView2 = itemView.findViewById(R.id.plu_choice);
            textView3 = itemView.findViewById(R.id.plu_quantity);
            textView4 = itemView.findViewById(R.id.plu_price);
            textView5 = itemView.findViewById(R.id.plu_amount);
            textView6 = itemView.findViewById(R.id.plu_discount);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_child_special2, parent, false);//要动态加载的布局
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String s = ss.get(position);

        DetailViewHolder detailViewHolder = (DetailViewHolder) holder;
        detailViewHolder.textView1.setText(s);
        detailViewHolder.textView2.setText(s);
        detailViewHolder.textView3.setText(s);
        detailViewHolder.textView4.setText(s);
        detailViewHolder.textView5.setText(s);
        detailViewHolder.textView6.setText(s);
    }

    @Override
    public int getItemCount() {
        return ss.size();
    }
}
