package com.example.listviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import top.august1996.ui.DrawableListView;
import top.august1996.ui.DrawableListView.OnLoadListener;
import top.august1996.ui.DrawableListView.RefreshListener;

public class DrawableListViewActivity extends Activity {
	
	private DrawableListView	mListView;
	private String[]			movies	= { "北京遇上西雅图之不二情书2", "华丽上班族", "三城记", "命中注定", "捉妖记", "骇客交锋", "黄金时代", "北京遇上西雅图",
			"晚秋", "极速天使", "北京遇上西雅图之不二情书2", "华丽上班族", "三城记", "命中注定", "捉妖记", "骇客交锋", "黄金时代", "北京遇上西雅图", "晚秋", "极速天使" };
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawable_list_view);
		mListView = (DrawableListView) findViewById(R.id.mListView);
		mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies));
		mListView.setRefreshListener(new RefreshListener() {
			
			@Override
			public void onRefresh() {
				new Thread() {
					public void run() {
						try {
							Thread.sleep(3000);
							DrawableListViewActivity.this.runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									mListView.finishRefresh();
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
		mListView.setOnLoadListener(new OnLoadListener() {
			
			@Override
			public void onLoad() {
				new Thread() {
					public void run() {
						try {
							Thread.sleep(3000);
							DrawableListViewActivity.this.runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									mListView.finishLoad();
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		});
	}
	
}
