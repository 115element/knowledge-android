package com.example.knowledge_android.recyclerview2.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.activity2.JumpAvtivity;
import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview2.bean.MapBean;

import java.util.ArrayList;
import java.util.List;

public class FoldAdapter extends RecyclerView.Adapter {

    private List<MapBean> mapBeanList = new ArrayList<>();//建立一个存放标题的列表
    // 本节继续带来的是Android系统服务中的LayoutInflater(布局服务)，说到布局，
    // 大家第一时间 可能想起的是写完一个布局的xml，然后调用Activity的setContentView()加载布局，
    // 然后把他显示 到屏幕上是吧~其实这个底层走的还是这个LayoutInflater，
    // 用的Android内置的Pull解析器来解析 布局。一般在Android动态加载布局或者添加控件用得较多.
    private LayoutInflater inflater;
    private Context context;//上下文


    //把要展示的数据源传进来，并赋值给一个全局变量mapBeanList,后续的操作都将在这个数据源的基础上进行
    public FoldAdapter(List<MapBean> mBeanList) {
        this.mapBeanList = mBeanList;
        Log.i("数据:", mapBeanList.toString());
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView parentTextview;

        public ParentViewHolder(View view) {
            super(view);//view参数就是RecyclerView子项最外层布局，也就是parent_caption
            parentTextview = view.findViewById(R.id.parent_textview);
            //通过findViewById()方法获取到布局中的实例
        }
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView childTextview;

        public ChildViewHolder(View view) {
            super(view);
            childTextview = view.findViewById(R.id.child_textview);
        }
    }


    //获取视图类型
    @Override
    public int getItemViewType(int position) {
        int viewType = mapBeanList.get(position).getViewType();
        Log.i("获取Bean中定义的类型：", viewType + "");
        return viewType;
    }


    /**
     * 动态加载布局文件，然后生成不同布局对应的holder实例,需要进行判断当前列表项的类型，来初始化不同的Holder
     * 根据onCreateViewHolder方法传来的viewType进行判断然后返回对应item布局的ViewHolder实例
     *
     * @param parent   – The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType – The view type of the new View.
     *                 【这个viewType就是调用当前类重写的getItemViewType()方法获取的，从而和Bean实现绑定】
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("新创建的视图类型:", viewType + "");
        if (context == null) {
            context = parent.getContext();
        }
        if (inflater == null) {//如果没有这一行判断则会出错
            inflater = LayoutInflater.from(context);
        }
        View view;
        switch (viewType) {
            case MapBean.TYPE_PARENT:
                //view=LayoutInflater.from(parent.getContect()).inflate() 则会出错
                view = inflater.inflate(R.layout.parent_caption, parent, false);//要动态加载的布局
                return new ParentViewHolder(view);
            case MapBean.TYPE_CHILD:
                view = inflater.inflate(R.layout.child_caption, parent, false);//要动态加载的布局
                return new ChildViewHolder(view);
        }
        return null;
    }


    /**
     * 用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
     *
     * @param holder   要绑定的视图
     * @param position 视图的位置
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MapBean mapBean;
        if (holder instanceof ParentViewHolder) {
            //如果是父类型，则要去找其对应的子类列表里面的个数并进行展示
            mapBean = mapBeanList.get(position);
            ((ParentViewHolder) holder).parentTextview.setText(mapBean.getCaption());
            ((ParentViewHolder) holder).parentTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();//找到对应的位置
                    MapBean mapBean = mapBeanList.get(position);//根据位置获取到实例
                    List<MapBean> childList = mapBean.getChildList();//获取实例对应的子项
                    if (childList.size() == 0) {
                        return;
                    }
                    if (mapBean.isExpand()) {//如果是展开的则删除子项.从position+1开始删除childList.size()个
                        for (int i = 1; i <= childList.size(); i++) {
                            mapBeanList.remove(position + 1);
                            notifyItemRemoved(position + 1);//更新状态
                        }
                        mapBean.setExpand(false);//将其设为已经折叠
                    } else {
                        //是折叠状态则进行添加，由于position是不变的，然后需要在下方一直插入，则需要倒着插入
                        for (int i = childList.size() - 1; i >= 0; i--) {
                            mapBeanList.add(position + 1, childList.get(i));
                            notifyItemInserted(position + 1);
                        }
                        mapBean.setExpand(true); //将其设置为已展开
                    }
                }
            });
        } else if (holder instanceof ChildViewHolder) {
            mapBean = mapBeanList.get(position);
            ((ChildViewHolder) holder).childTextview.setText(mapBean.getCaption());
            ((ChildViewHolder) holder).childTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("", "点击子组件");
                    Intent intent = new Intent();
                    intent.putExtra("caption", mapBean.getCaption());
                    intent.setClass(context, JumpAvtivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mapBeanList.size();
    }


}
