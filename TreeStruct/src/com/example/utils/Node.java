package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;

public class Node {
	private int id;

	private int pId = 0;
	private String name;

	private int level;

	private boolean isExpand = false;

	private int icon;

	private Node parent;
	private List<Node> children = new ArrayList<Node>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
		for (Node child : children) {
			child.setExpand(isExpand);
		}
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public boolean isParentExpand() {
		if (parent == null) {
			return false;
		} else {
			return parent.isExpand();
		}
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}

	public Node(int id, int pId, String name) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
	}
	
	public boolean isRoot(){
		return parent == null;
	}

}
