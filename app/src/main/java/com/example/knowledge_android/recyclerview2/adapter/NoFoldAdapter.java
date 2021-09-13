package com.example.knowledge_android.recyclerview2.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge_android.activity2.JumpAvtivity;
import com.example.knowledge_android.R;
import com.example.knowledge_android.recyclerview2.bean.MapBean;

import java.util.ArrayList;
import java.util.List;

public class NoFoldAdapter extends RecyclerView.Adapter {

    private List<MapBean> mapBeanList = new ArrayList<>();//建立一个存放标题的列表
    private LayoutInflater inflater;
    private Context context;//上下文


    //把要展示的数据源传进来，并赋值给一个全局变量mapBeanList,后续的操作都将在这个数据源的基础上进行
    //由于现在是一个一级标题套着多个小标题，则需要把数据按顺序取出来
    public NoFoldAdapter(List<MapBean> mBeanList) {
        int len = mBeanList.size();
        //this.mapBeanList = mBeanList;
        for (int i = 0; i < len; i++) {
            MapBean mapBean = mBeanList.get(i);
            mapBeanList.add(mapBean);
            List<MapBean> childList = mapBean.getChildList();//获取子项标题
            for (int j = 0; j < childList.size(); j++) {
                mapBeanList.add(childList.get(j));
            }
        }
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
                //view=LayoutInflater.from(parent.getContext()).inflate() 则会出错
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
        //通过position参数得到当前项的mapBean实例
        if (holder instanceof ParentViewHolder) {
            mapBean = mapBeanList.get(position);
            ((ParentViewHolder) holder).parentTextview.setText(mapBean.getCaption());
            ((ParentViewHolder) holder).parentTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "you clicked parent " + mapBean.getCaption(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof ChildViewHolder) {
            mapBean = mapBeanList.get(position);
            ((ChildViewHolder) holder).childTextview.setText(mapBean.getCaption());
            ((ChildViewHolder) holder).childTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG","点击二级组件");
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


    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (mapBeanList.size() > 0) {
            viewType = mapBeanList.get(position).getViewType();//返回当前位置对应的viewType，也就是Bean中定义的类型
        } else {
            viewType = super.getItemViewType(position);
        }
        Log.i("获取Bean中定义的类型：", viewType + "");
        return viewType;
    }

}
