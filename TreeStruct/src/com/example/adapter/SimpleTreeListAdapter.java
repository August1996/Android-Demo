package com.example.adapter;

import java.util.List;

import com.example.treestruct.R;
import com.example.utils.Node;
import com.example.utils.TreeHelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SimpleTreeListAdapter<T> extends TreeListAdapter<T> {

	public SimpleTreeListAdapter(Context context, ListView treeList, List<T> datas, int defaultExpandLevel)
			throws Exception {
		super(context, treeList, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.iconItem = (ImageView) convertView.findViewById(R.id.itemIcon);
			viewHolder.itemText = (TextView) convertView.findViewById(R.id.itemText);
			convertView.setTag(viewHolder);
		}
		viewHolder = (SimpleTreeListAdapter<T>.ViewHolder) convertView.getTag();
		if (node.getIcon() == -1) {
			viewHolder.iconItem.setVisibility(View.GONE);
		} else {
			viewHolder.iconItem.setVisibility(View.VISIBLE);
			viewHolder.iconItem.setImageResource(node.getIcon());
		}
		viewHolder.itemText.setText(node.getName());
		return convertView;
	}

	class ViewHolder {
		ImageView iconItem;
		TextView itemText;
	}

	public void addExtraNode(int position, String string) {
		Node node = visibleNodes.get(position);
		int indexOf = allNodes.indexOf(node);

		Node extraNode = new Node(-1, node.getId(), string);
		extraNode.setParent(node);
		extraNode.setpId(node.getId());
		node.getChildren().add(extraNode);
		allNodes.add(indexOf + 1, extraNode);
		
		visibleNodes = TreeHelper.filterVIsibleNodes(allNodes);
		
		notifyDataSetChanged();
	}

}
