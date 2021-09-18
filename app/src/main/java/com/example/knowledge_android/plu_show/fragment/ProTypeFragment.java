package com.example.knowledge_android.plu_show.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.knowledge_android.R;
import com.example.knowledge_android.plu_show.Model;
import com.example.knowledge_android.plu_show.adapter.GridViewAdapter;
import com.example.knowledge_android.plu_show.bean.TypeX;

import java.util.ArrayList;

public class ProTypeFragment extends Fragment {

	private ArrayList<TypeX> list;
	private GridView gridView;
	private GridViewAdapter adapter;
	private TypeX type;
	private String typename;
	private int icon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pro_type, null);
		gridView = (GridView) view.findViewById(R.id.listView);
		int index = getArguments().getInt("index");

		typename = Model.toolsList[index];
		icon = Model.iconList[index];

		((TextView) view.findViewById(R.id.toptype)).setText(typename);
		GetTypeList();
		adapter = new GridViewAdapter(getActivity(), list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			}
		});

		return view;
	}

	private void GetTypeList() {
		list = new ArrayList<TypeX>();
		for (int i = 1; i < 23; i++) {
			type = new TypeX(i, typename + i, icon);
			list.add(type);
		}
	}
}
