package com.example.knowledge_android.dialog.alertdialog_listview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.knowledge_android.R;

import java.util.List;


public class PluListView extends ListView {

    public static String pluId;

    public PluListView(Context context) {
        super(context);
        pluId = "";
    }

    public PluListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pluId = "";
    }

    public static class PluListAdapter extends BaseAdapter {
        List<HyiPlu> model;

        public List<HyiPlu> getModel() {
            return model;
        }

        public void setModel(List<HyiPlu> model) {
            this.model = model;
        }

        public PluListAdapter(List<HyiPlu> model) {
            this.model = model;
        }

        @Override
        public int getCount() {
            return model.size();
        }

        @Override
        public Object getItem(int position) {
            return model.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HyiPlu itemData = get(position);
            HyiPluViewHolder hyiPluViewHolder = null;
            if (convertView == null) {
                hyiPluViewHolder = new HyiPluViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plu_show, parent, false);
                TextView plu_id = convertView.findViewById(R.id.pluId);
                TextView plu_name = convertView.findViewById(R.id.pluName);

                plu_id.setText(String.valueOf(itemData.getPluId()));
                plu_name.setText(itemData.getName());

                hyiPluViewHolder.pluId = plu_id;
                hyiPluViewHolder.pluName = plu_name;
                convertView.setTag(hyiPluViewHolder);
            } else {
                hyiPluViewHolder = (HyiPluViewHolder) convertView.getTag();
            }
            if (itemData.isSelected()) {
                convertView.setBackgroundColor(Color.GRAY);
            } else {
                convertView.setBackgroundColor(Color.WHITE);
            }

            convertView.setOnClickListener(
                    v -> {
                        Log.i("TAG","点击商品:"+itemData.getPluId());

                        cancel(position);

                        pluId = itemData.getPluId();
                    }
            );
            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        public HyiPlu get(int position) {
            return model.get(position);
        }

        public void cancel(int position){
            for (int i = 0; i < model.size(); i++) {
                if (i == position) {
                    model.get(i).setSelected(true);
                } else {
                    model.get(i).setSelected(false);
                }
            }
            notifyDataSetChanged();
        }
    }

    public static class HyiPluViewHolder {
        private TextView pluId;
        private TextView pluName;
    }
}
