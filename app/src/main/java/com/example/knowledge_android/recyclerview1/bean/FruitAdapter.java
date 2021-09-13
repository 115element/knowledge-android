package com.example.knowledge_android.recyclerview1.bean;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.DiyViewHolder> {

    private List<Fruit> fruits;

    public List<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public FruitAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    //ViewHolder用来解决性能问题，ItemView暂存
    public static class DiyViewHolder extends RecyclerView.ViewHolder {

        ImageView fruitImage;
        TextView fruitName;

        public DiyViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }


    @NonNull
    @Override
    public DiyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "创建ViewHolder()...");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        DiyViewHolder viewHolder = new DiyViewHolder(itemView);


        //给列表上的组件，添加时间
        viewHolder.fruitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = fruits.get(position);
                Log.i("-","点击文字");
                Toast.makeText(v.getContext(), "你点击了文字", Toast.LENGTH_SHORT).show();

            }
        });
        viewHolder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = fruits.get(position);
                Log.i("-","点击图片");
                Toast.makeText(v.getContext(), "你点击了图片", Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiyViewHolder holder, int position) {
        Log.i("TAG", "绑定ViewHolder()...");
        Fruit fruit = fruits.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }


    //获取数据的总数量
    @Override
    public int getItemCount() {
        Log.i("TAG", "获取列表数据总数量。。。");
        return fruits.size();
    }

    /**
     * 第一个执行：getItemCount()方法
     * 这个方法主要是用来返回数据源的长度，也就是告诉RecyclerView他有多少个子项
     *
     * 第二个执行：onCreateViewHolder()方法
     * 这个函数主要是用来加载子项布局（fruit_item），然后创建ViewHolder实例并把子项布局传入到构造函数中，最后返回ViewHolder实例。
     *
     * 第三个执行：onBindViewHolder()方法
     * 这个方法是用来对传入的子项布局进行赋值的，也就是说当子项布局被传入进来，是它让布局有了水果名、水果图片。它会在每个布局被滚动到屏幕的时候执行。
     * 在本例子中，是通过position参数得到当前项的Fruit实例，然后再把数据设置到ViewHolder的ImageView和TextView里。
     */
}
