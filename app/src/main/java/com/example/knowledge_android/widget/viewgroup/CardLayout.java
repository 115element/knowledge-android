package com.example.knowledge_android.widget.viewgroup;

import android.content.Context;
import android.view.View;

import com.arasthel.swissknife.annotations.OnUIThread;
import com.blankj.utilcode.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CardLayout extends ElasticGridLayout{
    int visibleChildIndex;

    private List<View> children = new ArrayList<>();

    public CardLayout(Context context) {
        super(context);
        this.rows = new ArrayList();
        this.rows.add(1);
        this.columns = new ArrayList();
        this.columns.add(1);
    }

    void add(View view) {
        super.add(view, 1, 1, 1, 1);
        children.add(view);
    }

    @OnUIThread
    void refreshVisibilitiesOfChildren() {
        for (int i = 0; i < children.size(); i++) {
            children.get(i).setVisibility(i == visibleChildIndex ? VISIBLE : GONE);
        }
    }

    void flip() {
        visibleChildIndex = ++visibleChildIndex % children.size();
        refreshVisibilitiesOfChildren();
    }

    void show(int index) {
        visibleChildIndex = index;
        refreshVisibilitiesOfChildren();
    }

    void removeFirst() {
        if (!CollectionUtils.isEmpty(children)) {
            removeView(children.get(0));
            children.remove(0);
            showFirst();
        }
    }

    void showFirst() {
        visibleChildIndex = 0;
        refreshVisibilitiesOfChildren();
    }

    void showSecond() {
        visibleChildIndex = 1;
        refreshVisibilitiesOfChildren();
    }

    void showThird() {
        visibleChildIndex = 2;
        refreshVisibilitiesOfChildren();
    }

    void showFourth() {
        visibleChildIndex = 3;
        refreshVisibilitiesOfChildren();
    }
}
