package com.example.recyclerviw;

public interface GenericAdapter {
	void addData(int pos);
	void delData(int pos);
	
	void setOnItemClickListener(OnItemClickListener onItemClickListener);
}
