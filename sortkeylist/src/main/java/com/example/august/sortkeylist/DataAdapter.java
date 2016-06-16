package com.example.august.sortkeylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by August on 16/5/11.
 */
public class DataAdapter extends BaseAdapter {

    private DataBean[] mDatas;

    /**
     * 用来保存已经每个字母的第一个数据的位置
     */
    private Map<String, Integer> marks = new HashMap<>();


    public DataAdapter(DataBean[] mDatas) {
        this.mDatas = mDatas;

        for (int i = 0; i < mDatas.length; i++) {
            if (marks.get(String.valueOf(mDatas[i].getFirstLetter())) == null) {
                marks.put(String.valueOf(mDatas[i].getFirstLetter()), i + 1);
            }
        }
        /**
         * 初始化索引所有字母的第一个数据的位置
         * 键值为字母,值为位置+1,因为Map默认不存在时返回0,所以我们位置需要从1开始
         */


    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int i) {
        return mDatas[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data, null);
            holder = new ViewHolder();
            holder.data = (TextView) view.findViewById(R.id.data);
            holder.sortKey = (TextView) view.findViewById(R.id.sortKey);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.data.setText(mDatas[i].getData());
        /**
         * 上面都是普通Adapter的代码
         */


        /**
         * 下面是开始判断,当当前item的位置等于刚开始索引出来的位置时,我们把sortKey给显示出来
         * 否则隐藏掉
         */
        if (marks.get(String.valueOf(mDatas[i].getFirstLetter())) == i + 1) {
            holder.sortKey.setText(String.valueOf(mDatas[i].getFirstLetter()));
            holder.sortKey.setVisibility(View.VISIBLE);
        } else {
            holder.sortKey.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder {
        TextView data;
        TextView sortKey;
    }

    public Integer getPosition(String s) {
        Integer result = marks.get(s);
        if (result != null) {
            result = result - 1;
        }
        return result;
    }

}
