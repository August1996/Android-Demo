package com.example.august.commonadapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mListView = (ListView) findViewById(R.id.mListView);

        List<ItemBean> mDatas = new ArrayList<ItemBean>();
        for (int i = 'A'; i < 'Z'; i++) {
            ItemBean itemBean = new ItemBean(R.drawable.pic, String.valueOf((char) i));
            mDatas.add(itemBean);
        }

        GeneralParentAdapter<ItemBean> mAdapter = new GeneralParentAdapter<ItemBean>(mDatas, MainActivity.this, R.layout.item_text) {
            @Override
            public void setContent(ViewHolder holder, List<ItemBean> mDatas, int postion) {
                holder.setText(R.id.mTextView, mDatas.get(postion).text);
                holder.setBitmap(R.id.mImageView, BitmapFactory.decodeResource(getResources(), R.drawable.pic));
            }
        };
        mListView.setAdapter(mAdapter);


    }
}
