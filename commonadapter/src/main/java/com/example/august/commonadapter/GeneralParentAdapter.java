package com.example.august.commonadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by August on 16/4/9.
 */
public abstract class GeneralParentAdapter<T> extends BaseAdapter {

    private List<T> mDatas;
    private Context mContext;
    private int layoutID;
    private ViewHolder holder;

    public GeneralParentAdapter(List<T> mDatas, Context mContext, int layoutID) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.layoutID = layoutID;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = ViewHolder.getHolder(mContext, convertView, layoutID, parent);
        setContent(holder, mDatas, position);
        return holder.getConverview();
    }

    public abstract void setContent(ViewHolder holder, List<T> mDatas, int position);


}
