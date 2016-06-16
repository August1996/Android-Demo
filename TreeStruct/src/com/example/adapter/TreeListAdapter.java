package com.example.adapter;

import java.util.List;

import com.example.utils.Node;
import com.example.utils.TreeHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class TreeListAdapter<T> extends BaseAdapter {
	protected Context context;
	protected ListView treeList;
	protected List<Node> allNodes;
	protected List<Node> visibleNodes;
	protected LayoutInflater inflater;

	private OnNodeItemClickListener onNodeItemClickListener;

	public interface OnNodeItemClickListener {
		public void onClick(Node node, int position);
	}

	public void setOnNodeItemClickListener(OnNodeItemClickListener onNodeItemClickListener) {
		this.onNodeItemClickListener = onNodeItemClickListener;
	}

	public TreeListAdapter(Context context, ListView treeList, List<T> datas, int defaultExpandLevel) throws Exception {

		this.context = context;
		inflater = LayoutInflater.from(context);
		this.treeList = treeList;

		allNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		visibleNodes = TreeHelper.filterVIsibleNodes(allNodes);

		treeList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				expandOrCollapse(position);
				if (onNodeItemClickListener != null) {
					onNodeItemClickListener.onClick(visibleNodes.get(position), position);
				}
			}
		});
	}

	private void expandOrCollapse(int position) {
		Node n = visibleNodes.get(position);
		if (n != null) {
			if (n.isLeaf()) {
				return;
			} else {
				n.setExpand(!n.isExpand());
				visibleNodes = TreeHelper.filterVIsibleNodes(allNodes);
				notifyDataSetChanged();
			}
		}
	}

	@Override
	public int getCount() {
		return visibleNodes.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Node node = visibleNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);

		convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
		return convertView;
	}

	public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);

}
