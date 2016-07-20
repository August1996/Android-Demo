package com.example.listviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void test(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.btn1:
				intent.setClass(this, ImageListViewActivity.class);
				break;
			case R.id.btn2:
				intent.setClass(this, DrawableListViewActivity.class);
				break;
			default:
				break;
		}
		startActivity(intent);
		
	}
	
}
