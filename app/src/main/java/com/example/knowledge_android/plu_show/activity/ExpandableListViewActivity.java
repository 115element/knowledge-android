package com.example.knowledge_android.plu_show.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.example.knowledge_android.plu_show.Model;
import com.example.knowledge_android.plu_show.adapter.ExpandableListXAdapter;

public class ExpandableListViewActivity extends AppCompatActivity {

	private ExpandableListView expandableListView;

	private ExpandableListXAdapter adapter;

	private List<Map<String, Object>> list;
	private String[][] child_text_array;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_listview);

		init();
		initModle();
		setListener();
	}

	private void init() {
		expandableListView = (ExpandableListView) findViewById(R.id.list);

		child_text_array = Model.EXPANDABLE_MORELIST_TXT;
	}

	private void setListener() {
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Toast.makeText(getApplicationContext(),
						child_text_array[groupPosition][childPosition],
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	private void initModle() {
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < Model.EXPANDABLE_LISTVIEW_TXT.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", Model.EXPANDABLE_LISTVIEW_IMG[i]);
			map.put("txt", Model.EXPANDABLE_LISTVIEW_TXT[i]);
			list.add(map);
		}
		adapter = new ExpandableListXAdapter(this, list, child_text_array);
		expandableListView.setAdapter(adapter);
	}

}
