package com.example.august.sortkeylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private SortListView mSortListView;
    private DataBean[] mDatas;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.mListView);
        mSortListView = (SortListView) findViewById(R.id.mSortList);
        mDatas = new DataBean[500];
        for (int i = 0; i < 500; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setData(String.valueOf((char) (65 + Math.random() * 26))
                    + String.valueOf((char) (65 + Math.random() * 26))
                    + String.valueOf((char) (65 + Math.random() * 26)));
            mDatas[i] = dataBean;
        }

        Arrays.sort(mDatas);

        mAdapter = new DataAdapter(mDatas);
        mSortListView.setmOnTouchListener(new SortListView.OnTouchListener() {
            @Override
            public void onTouch(String s) {
                Integer index = mAdapter.getPosition(s);
                if (index != null) {
                    mListView.setSelection(index);
                }
            }
        });
        mListView.setAdapter(mAdapter);
    }


}
