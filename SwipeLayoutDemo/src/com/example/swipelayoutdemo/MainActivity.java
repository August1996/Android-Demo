package com.example.swipelayoutdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import top.august1996.ui.SwipeLayout;
import top.august1996.ui.SwipeLayout.OnButtonClickListener;

public class MainActivity extends Activity {
	
	private ListView				mListView;
	private List<String>			mData	= new ArrayList<String>();
	private ArrayAdapter<String>	mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.mListView);
		for (int i = 0; i < 100; i++) {
			mData.add("August199" + i);
		}
		
		mAdapter = new ArrayAdapter<String>(this, R.layout.item, mData) {
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				
				TextView mTextView;
				
				if (convertView == null) {
					convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null, false);
					mTextView = (TextView) convertView.findViewById(R.id.name);
					convertView.setTag(mTextView);
				} else {
					mTextView = (TextView) convertView.getTag();
				}
				((SwipeLayout) convertView).setOnButtonClickListener(new OnButtonClickListener() {
					
					@Override
					public void onRightClick() {
						Log.i("TAG", "right button clicked:" + mData.get(position));
					}
					
					@Override
					public void onLeftClick() {
						Log.i("TAG", "left button clicked:" + mData.get(position));
					}
				});
				mTextView.setText(mData.get(position));
				return convertView;
			}
		};
		mListView.setAdapter(mAdapter);
	}
}
