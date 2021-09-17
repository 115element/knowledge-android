package com.example.knowledge_android.girdview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.knowledge_android.R;

import java.util.List;


/**
 * GridView适配器，GridView必须要一个适配器
 */
// ProvinceAdapter继承自BaseAdapter，
// 有几个必须实现的方法getCount()，getItem(int position)，getItemId(int position)
// 和getView(int position, View convertView, ViewGroup parent)。
// 其中，getCount()返回需要展示的GridView的项数。
// getItem(int position)返回给定位置的数据对象。
// getItemId(int position)返回该项的行id。
// getView(int position, View convertView, ViewGroup parent)是必须要实现的方法，
// 该方法控制GridView中数据项的显示，方法中的convertView视图是被复用的视图，在实现时对其进行判断，如果为null，则新建视图，否则直接复用视图。
public class ProvinceAdapter extends BaseAdapter {

    private List<ProvinceBean> provinceBeanList;
    private LayoutInflater layoutInflater;

    public ProvinceAdapter(Context context, List<ProvinceBean> provinceBeanList) {
        this.provinceBeanList = provinceBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    //返回需要展示的GridView的项数
    @Override
    public int getCount() {
        return provinceBeanList.size();
    }

    //返回给定位置的数据对象
    @Override
    public Object getItem(int position) {
        return provinceBeanList.get(position);
    }

    //返回该项的行id
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // getView(int position, View convertView, ViewGroup parent)是必须要实现的方法，
    // 该方法控制GridView中数据项的显示，方法中的convertView视图是被复用的视图，
    // 在实现时对其进行判断，如果为null，则新建视图，否则直接复用视图。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.province_grid_view_item_layout, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProvinceBean provinceBean = provinceBeanList.get(position);
        if (provinceBean != null) {
            holder.text.setText(provinceBean.getName());
            holder.text.setBackgroundResource(provinceBean.getColor());
        }
        return convertView;
    }

    //ViewHold用于存储创建的视图，重复利用，增加执行效率
    public static class ViewHolder {
        TextView text;
    }

}
