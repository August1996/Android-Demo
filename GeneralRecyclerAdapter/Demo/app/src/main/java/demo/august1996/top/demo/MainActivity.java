package demo.august1996.top.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerAdapter mRecyclerAdapter;
    List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        mDatas = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            mDatas.add(String.valueOf((char) i));
        }
        mRecyclerAdapter = new RecyclerAdapter(mDatas, R.layout.item);
        mRecyclerAdapter.setOnItemClickListener(new GenericRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Click item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    class RecyclerAdapter extends GenericRecyclerAdapter<String> {

        public RecyclerAdapter(List<String> mDatas, int layoutID) {
            super(mDatas, layoutID);
        }

        @Override
        public void setData(ViewHolder holder, List<String> mDatas, int position) {

        }


    }
}
