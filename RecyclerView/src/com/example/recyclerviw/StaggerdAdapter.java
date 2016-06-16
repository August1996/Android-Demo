package com.example.recyclerviw;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;

public class StaggerdAdapter extends RecyclerView.Adapter<MyViewHolder> implements GenericAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mDatas;
	private OnItemClickListener mOnItemClickListener;

	public StaggerdAdapter(Context mContext, List<String> mDatas) {
		this.mContext = mContext;
		this.mDatas = mDatas;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	@Override
	public void onBindViewHolder(final MyViewHolder arg0, final int arg1) {
		LayoutParams lps = arg0.textView.getLayoutParams();
		lps.height = (int) (100 + Math.random() * 300);
		arg0.textView.setLayoutParams(lps);
		arg0.textView.setText(mDatas.get(arg1));
		if (mOnItemClickListener != null) {

			arg0.textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnItemClickListener.onItemClick(arg0.textView, arg0.getLayoutPosition());
				}
			});

			arg0.textView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					mOnItemClickListener.onItemLongClick(arg0.textView, arg0.getLayoutPosition());
					return false;
				}
			});

		}

	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = mInflater.inflate(R.layout.simple_item_textview, arg0, false);
		MyViewHolder myViewHolder = new MyViewHolder(view);
		return myViewHolder;
	}

	public void addData(int pos) {
		mDatas.add("Insert" + pos);
		notifyItemInserted(pos);
	}

	public void delData(int pos) {
		mDatas.remove(pos);
		notifyItemRemoved(pos);
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

}
