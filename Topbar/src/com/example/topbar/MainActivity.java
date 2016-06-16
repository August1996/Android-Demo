package com.example.topbar;

import com.example.topbar.Topbar.TopBarOnClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity implements TopBarOnClickListener {

	private Topbar topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		topbar = (Topbar) findViewById(R.id.tb);
		topbar.setOnClickListener(this);
	}

	@Override
	public void leftClick() {
		Toast.makeText(this, "返回", Toast.LENGTH_LONG).show();
	}

	@Override
	public void rightClick() {
		Toast.makeText(this, "确定", Toast.LENGTH_LONG).show();
	}

}
