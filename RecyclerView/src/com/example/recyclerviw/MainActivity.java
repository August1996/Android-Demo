package com.example.recyclerviw;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity {

	LinearLayout container;
	RecyclerView mRecyclerView;

	List<String> mDatas;
	SimpleAdapter mSimpleAdapter;
	StaggerdAdapter mStaggerdAdapter;
	Adapter<MyViewHolder> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initDatas();
		mSimpleAdapter = new SimpleAdapter(MainActivity.this, mDatas);
		mStaggerdAdapter = new StaggerdAdapter(MainActivity.this, mDatas);
		mAdapter = mSimpleAdapter;
		mRecyclerView.setAdapter(mAdapter);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayout.VERTICAL,
				false);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		// mRecyclerView.addItemDecoration(new
		// DividerItemDecoration(MainActivity.this, LinearLayout.VERTICAL));

		mSimpleAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemLongClick(View view, int position) {
				mSimpleAdapter.delData(position);
			}

			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, "Click" + position, Toast.LENGTH_SHORT).show();
			}
		});

		mStaggerdAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemLongClick(View view, int position) {
				mStaggerdAdapter.delData(position);
			}

			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, "Click" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initDatas() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'Z'; i++) {
			mDatas.add((char) i + "");
		}
	}

	private void initView() {
		container = (LinearLayout) findViewById(R.id.container);
		mRecyclerView = new RecyclerView(MainActivity.this);
		mRecyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		container.addView(mRecyclerView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_listView:
			mAdapter = mSimpleAdapter;
			mRecyclerView.setAdapter(mAdapter);
			mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
			break;
		case R.id.action_hGridView:
			mAdapter = mSimpleAdapter;
			mRecyclerView.setAdapter(mAdapter);
			mRecyclerView
					.setLayoutManager(new GridLayoutManager(MainActivity.this, 5, GridLayoutManager.HORIZONTAL, false));
			break;
		case R.id.action_vGirdView:
			mAdapter = mSimpleAdapter;
			mRecyclerView.setAdapter(mAdapter);
			mRecyclerView
					.setLayoutManager(new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.VERTICAL, false));
			break;
		case R.id.action_sGirdView:
			mAdapter = mStaggerdAdapter;
			mRecyclerView.setAdapter(mAdapter);
			mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
			break;
		case R.id.action_add:
			((GenericAdapter) mAdapter).addData(mDatas.size() - 1);
			break;
		case R.id.action_del:
			((GenericAdapter) mAdapter).delData(0);
			break;
		default:
			break;
		}
		return true;
	}

}
