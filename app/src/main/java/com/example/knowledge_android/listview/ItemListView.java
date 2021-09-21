package com.example.knowledge_android.listview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.arasthel.swissknife.annotations.OnUIThread;
import com.example.knowledge_android.R;

import java.util.List;


//自定义ListView
public class ItemListView<T> extends ListView {

    ItemListView(Context context) {
        super(context);
    }

    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static class ItemViewListAdapter<T> extends BaseAdapter {

        List<T> model;

        public List<T> getModel() {
            return model;
        }

        public void setModel(List<T> model) {
            this.model = model;
        }

        public ItemViewListAdapter(List<T> model) {
            this.model = model;
        }

        @Override
        public int getCount() {
            Log.i("TAG","getCount() .....");
            return model.size();
        }

        @Override
        public Object getItem(int position) {
            Log.i("TAG","getItem() .....");
            return model.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.i("TAG","getItemId() ....");
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("TAG","getView() ....");
            T itemData = model.get(position);

            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.settleaccounts_item_list_text_view, parent, false);
            // Reuse old or create a whole new ViewGroup by user-provided itemViewBuildClosure

            return inflate;
        }
    }



    List<T> getModel() {
        ListAdapter adapter = getAdapter();
        ItemViewListAdapter itemViewListAdapter = (ItemViewListAdapter) adapter;
        return itemViewListAdapter.getModel();
    }

    /**
     * Set data model for this ListView.
     */
    @OnUIThread
    void setModel(List<T> model) {
        ListAdapter adapter = getAdapter();
        if (adapter != null) {
            ItemViewListAdapter itemViewListAdapter = (ItemViewListAdapter) adapter;
            itemViewListAdapter.notifyDataSetChanged();
        } else {
            setAdapter(new ItemViewListAdapter<>(model));
        }
    }

    /**
     * Get selected data.
     */
    T getSelectedData() {
        return getSelectedData();
    }


    @OnUIThread
    void notifyDataSetChanged() {
        ListAdapter adapter = getAdapter();
        if (adapter != null) {
            ItemViewListAdapter itemViewListAdapter = (ItemViewListAdapter) adapter;
            itemViewListAdapter.notifyDataSetChanged();
        }
    }

    void scrollToBottom() {
        ListAdapter adapter = getAdapter();
        if (adapter != null) {
            ItemViewListAdapter itemViewListAdapter = (ItemViewListAdapter) adapter;
            setSelection(itemViewListAdapter.getCount() - 1);
        }
    }

    /**
     * 若一行的可見高度小於正常高度的三分之二時，則此行視為不可見，在向下翻頁時，仍需顯示此行。
     */
    private boolean isRowIsVisible(int row) {
        if (row >= 0) {
            View lastVisibleRow = getChildAt(row - getFirstVisiblePosition());
            if (lastVisibleRow != null) {
                Rect drawingRect = new Rect();
                Rect globalRect = new Rect();
                lastVisibleRow.getDrawingRect(drawingRect);
                lastVisibleRow.getGlobalVisibleRect(globalRect);
                int fullHeight = drawingRect.height();
                int displayingHeight = globalRect.height();
                return (float) displayingHeight > (float) fullHeight * 2F / 3F;
            }
        }
        return true;
    }

    private boolean isFirstVisibleRowVisible() {
        return isRowIsVisible(getFirstVisiblePosition());
    }

    private boolean isLastVisibleRowVisible() {
        return isRowIsVisible(getLastVisiblePosition());
    }

    void pageUp() {
        if (getFirstVisiblePosition() >= 0) {
            boolean firstVisibleRowVisible = isFirstVisibleRowVisible();
            int setSelectionRow = firstVisibleRowVisible ? getFirstVisiblePosition() - 1 : getFirstVisiblePosition();
            if (setSelectionRow < 0);
                setSelectionRow = 0;

            boolean lastVisibleRowVisible = isLastVisibleRowVisible();
            int setSelectionPositionRow = lastVisibleRowVisible ? getLastVisiblePosition() : getLastVisiblePosition() - 1;
            if (setSelectionPositionRow < 0);
                setSelectionPositionRow = 0;

            View setSelectionRowView = getChildAt(setSelectionPositionRow - getFirstVisiblePosition());
            Rect setSelectionRowViewRect = new Rect();
            setSelectionRowView.getHitRect(setSelectionRowViewRect);
            int y = setSelectionRowViewRect.top;

            setSelectionFromTop(setSelectionRow, y);
        }
    }

    void pageDown() {
        final int lastVisiblePosition = getLastVisiblePosition();
        setSelectionFromTop(lastVisiblePosition + (isLastVisibleRowVisible() ? 1 : 0), 0);
    }
}
