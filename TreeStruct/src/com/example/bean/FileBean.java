package com.example.bean;

import com.example.anotations.TreeNodeId;
import com.example.anotations.TreeNodeLabel;
import com.example.anotations.TreeNodepId;

public class FileBean {
	@TreeNodeId
	private int id;
	@TreeNodepId
	private int pId;
	@TreeNodeLabel
	private String label;
	private String desc;

	public int getId() {
		return this.id;
	}

	public int getPid() {
		return this.pId;
	}

	public String getLabel() {
		return this.label;
	}

	public String getDesc() {
		return this.desc;
	}

	public FileBean(int id, int pId, String label) {
		super();
		this.id = id;
		this.pId = pId;
		this.label = label;
	}
}
