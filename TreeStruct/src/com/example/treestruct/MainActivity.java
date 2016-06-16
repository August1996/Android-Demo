package com.example.treestruct;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.SimpleTreeListAdapter;
import com.example.adapter.TreeListAdapter.OnNodeItemClickListener;
import com.example.bean.FileBean;
import com.example.utils.Node;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView listView;
	private SimpleTreeListAdapter<FileBean> adapter;
	private List<FileBean> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.fileTree);
		initDatas();

		try {
			adapter = new SimpleTreeListAdapter<FileBean>(MainActivity.this, listView, datas, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		listView.setAdapter(adapter);
		adapter.setOnNodeItemClickListener(new OnNodeItemClickListener() {

			@Override
			public void onClick(Node node, int position) {
				if (node.isLeaf()) {
					Toast.makeText(MainActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
				} else {

				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				final EditText et = new EditText(MainActivity.this);
				new AlertDialog.Builder(MainActivity.this).setTitle("Add Node").setView(et).setPositiveButton("Add", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						adapter.addExtraNode(position,et.getText().toString());
					}
				}).setNegativeButton("Cancel", null).show();
				return true;
			}
		});
	}

	void initDatas() {
		datas = new ArrayList<FileBean>();
		FileBean fileBean = null;
		fileBean = new FileBean(1, 0, "根目录1");
		datas.add(fileBean);
		fileBean = new FileBean(2, 0, "根目录2");
		datas.add(fileBean);
		fileBean = new FileBean(3, 0, "根目录3");
		datas.add(fileBean);
		fileBean = new FileBean(4, 1, "子目录1-1");
		datas.add(fileBean);
		fileBean = new FileBean(5, 1, "子目录1-2");
		datas.add(fileBean);

		fileBean = new FileBean(6, 3, "子目录3-1");
		datas.add(fileBean);

	}
}
