package demo.august1996.top.databingdingusage.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import demo.august1996.top.databingdingusage.R;
import demo.august1996.top.databingdingusage.databinding.ActivityImportViewBinding;

public class ImportViewActivity extends AppCompatActivity {


    private ArrayList<String> mList;
    private HashMap<String, String> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_view);

        mList = new ArrayList<>();
        mMap = new HashMap<>();

        for (int i = 0; i < 2; i++) {
            mList.add("列表" + i);
            mMap.put(String.valueOf(i), "集合" + i);
        }

        ActivityImportViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_import_view);
        binding.setList(mList);
        binding.setMap(mMap);

    }
}
