package com.example.listviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import top.august1996.ui.ImageListView;

public class ImageListViewActivity extends Activity {
	
	private ImageListView	mListView;
	private String[]		movies	= { "北京遇上西雅图之不二情书2", "华丽上班族", "三城记", "命中注定", "捉妖记", "骇客交锋", "黄金时代", "北京遇上西雅图", "晚秋",
			"极速天使", "北京遇上西雅图之不二情书2", "华丽上班族", "三城记", "命中注定", "捉妖记", "骇客交锋", "黄金时代", "北京遇上西雅图", "晚秋", "极速天使" };
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list_view);
		mListView = (ImageListView) findViewById(R.id.mListView);
		mListView.setImageResource(R.drawable.tw);
		mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies));
	}
	
}
