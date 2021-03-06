package com.example.knowledge_android.secondary_list2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class QRecyclerViewEmptySupport extends RecyclerView {

    private volatile boolean isRealEmpty;

    private View mEmptyView;


    private AdapterDataObserver mObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter == null || mEmptyView == null) {
                return;
            }

            if (adapter.getItemCount() == 0) {
                isRealEmpty = true;
                QRecyclerViewEmptySupport.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRealEmpty) {
                            mEmptyView.setVisibility(VISIBLE);
                            QRecyclerViewEmptySupport.this.setVisibility(GONE);
                        }
                    }
                }, 300);
            } else {
                isRealEmpty = false;
                mEmptyView.setVisibility(GONE);
                QRecyclerViewEmptySupport.this.setVisibility(VISIBLE);
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
        }
    };

    public QRecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public QRecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QRecyclerViewEmptySupport(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmptyView(View view) {
        this.mEmptyView = view;

        if (mEmptyView != null) {
            //???????????????The specified child already has a parent. You must call removeView() on the child's parent first.
            ViewGroup parent = (ViewGroup) mEmptyView.getParent();
            if (parent != null) {
                parent.removeView(mEmptyView);
            }

            ((ViewGroup) this.getParent()).addView(mEmptyView, ((ViewGroup) this.getParent()).indexOfChild(this), getLayoutParams());
        }
        mObserver.onChanged();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        Adapter<?> oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }

        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
        }
        mObserver.onChanged();
    }
}
