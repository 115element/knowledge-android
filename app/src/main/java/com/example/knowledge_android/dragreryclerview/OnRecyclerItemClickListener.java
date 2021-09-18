package com.example.knowledge_android.dragreryclerview;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{


    //Android RecyclerView单击、长按事件：基于OnItemTouchListener + GestureDetector标准实现
    //
    // Android RecyclerView虽然拥有ListView绝大多数的功能，
    // 但Android RecyclerView却没有实现像ListView那样的点击事件、长按事件的标准实现方式，我在附录文章1中介绍的方式，
    // 实现了典型的RecyclerView的item点击事件，大致的思路是通过为RecyclerView的ViewHolder添加View.OnClickListener事件达到点击事件的监听，
    // 这种实现方式可以正常工作，但不太标准。现在给出一种较为规范的Android官方实现。
    // 翻了Android RecyclerView的官方文档，这文档中确实没有提供像ListView的OnItem点击事件，
    // 但是注意到有一个addOnItemTouchListener，根据官方文档的描述，addOnItemTouchListener是Android官方文档中留下的点击事件的线索入口，
    // 文档中也暗示开发者应该从这个事件监听接口中实现所需的逻辑代码。但是直接new一个对象传递过去，则需要自己解析Android Touch Event复杂的处理，
    // 所幸Android官方给出了RecyclerView.SimpleOnItemTouchListener，顾名思义，就是一个简化的OnItemTouchListener。
    // 虽然有了SimpleOnItemTouchListener，到此还没完，因为SimpleOnItemTouchListener只是事件拦截处理的入口，
    // 真正要做事情的主角是：GestureDetector。通过GestureDetector里面的拦截到的所托管的单击、长按事件，才最终实现了RecyclerView的单击、长按事件。
    //[Gesture]  n. 手势，姿势；姿态，表示   V. 打手势，用动作示意；打手势，用动作示意   vt. 用动作表示
    //[Detector] n. 探测器；检测器；发现者；侦察器
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onItemClick(vh);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                onItemLongClick(vh);
            }
        }
    }

    //点击事件
    public abstract void onItemClick(RecyclerView.ViewHolder vh);

    //长按事件
    public abstract void onItemLongClick(RecyclerView.ViewHolder vh);
}
