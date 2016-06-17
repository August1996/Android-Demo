package demo.august1996.top.databingdingusage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import demo.august1996.top.databingdingusage.R;
import demo.august1996.top.databingdingusage.adapter.UserAdapter;
import demo.august1996.top.databingdingusage.bean.Student;
import demo.august1996.top.databingdingusage.bean.User;

public class ListViewActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Student> mData;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mListView = (ListView) findViewById(R.id.mListView);

        mData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mData.add(new Student("王" + i, "" + i, i % 2 == 0));
        }

        mAdapter = new UserAdapter(mData);
        mListView.setAdapter(mAdapter);

    }
}
