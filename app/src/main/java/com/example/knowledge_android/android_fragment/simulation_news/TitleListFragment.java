package com.example.knowledge_android.android_fragment.simulation_news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.knowledge_android.R;

//用来显示标题的列表
public class TitleListFragment extends ListFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //设置ListView为单选模式
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        //给ListView设置Adapter
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_text_view, DataUtils.TITLES));

        //默认选中第一个Item
        getListView().setItemChecked(0, true);
        //并展示右侧
        showDetail(0);
    }

    //监听ListView的Item
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
       showDetail(position);
    }

    public void showDetail(int position){
        //创建DetailFragment
        DetailFragment detailFragment = new DetailFragment();
        //将对应的详情数据携带过去
        Bundle args = new Bundle();
        args.putString("DETAIL", DataUtils.DETAILS[position]);
        detailFragment.setArguments(args);
        //将其替换到id为fl_main_container的容器布局中
        getFragmentManager().beginTransaction().replace(R.id.fl_main_container, detailFragment).commit();
    }
}
