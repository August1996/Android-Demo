package com.example.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.example.anotations.TreeNodeId;
import com.example.anotations.TreeNodeLabel;
import com.example.anotations.TreeNodepId;
import com.example.treestruct.R;

public class TreeHelper {
	static <T> List<Node> convertDates2Nodes(List<T> datas) throws Exception {
		List<Node> nodes = new ArrayList<Node>();
		for (T t : datas) {
			Class clazz = t.getClass();
			Field[] fields = clazz.getDeclaredFields();
			Node node = null;
			int id = -1;
			int pId = -1;
			String label = null;

			for (Field field : fields) {
				field.setAccessible(true);
				if (field.getAnnotation(TreeNodeId.class) != null) {
					id = field.getInt(t);
				} else if (field.getAnnotation(TreeNodepId.class) != null) {
					pId = field.getInt(t);
				} else if (field.getAnnotation(TreeNodeLabel.class) != null) {
					label = (String) field.get(t);
				}
			}
			node = new Node(id, pId, label);
			nodes.add(node);
		}

		for (int i = 0; i < nodes.size(); i++) {
			Node m = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {
				Node n = nodes.get(j);
				if (n.getpId() == m.getId()) {
					m.getChildren().add(n);
					n.setParent(m);
					n.setpId(m.getId());
				} else if (n.getId() == m.getpId()) {
					m.getChildren().add(n);
					m.setParent(n);
					m.setpId(n.getId());
				}
			}
		}

		for (Node n : nodes) {
			setNodeIcon(n);
		}
		return nodes;
	}

	public static void setNodeIcon(Node n) {
		if (n.getChildren().size() > 0 && n.isExpand()) {
			n.setIcon(R.drawable.tree_ex);
		} else if (n.getChildren().size() > 0 && !n.isExpand()) {
			n.setIcon(R.drawable.tree_ec);
		} else {
			n.setIcon(-1);
		}
	}

	public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws Exception {
		List<Node> result = new ArrayList<Node>();
		List<Node> nodes = convertDates2Nodes(datas);
		List<Node> rootNodes = getRootNodes(nodes);

		for (Node node : rootNodes) {
			addNode(result, node, defaultExpandLevel, 1);
		}

		return result;
	}

	private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
		result.add(node);

		if (defaultExpandLevel >= currentLevel) {
			node.setExpand(true);
		}

		if (node.isLeaf()) {
			return;
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
		}
	}

	public static List<Node> getRootNodes(List<Node> nodes) {
		List<Node> root = new ArrayList<Node>();

		for (Node node : nodes) {
			if (node.isRoot()) {
				root.add(node);
			}
		}
		return root;
	}

	public static List<Node> filterVIsibleNodes(List<Node> nodes) {
		List<Node> result = new ArrayList<Node>();

		for (Node node : nodes) {
			if (node.isRoot() || node.isParentExpand()) {
				setNodeIcon(node);
				result.add(node);
			}
		}

		return result;
	}
}
