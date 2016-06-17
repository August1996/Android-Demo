package demo.august1996.top.databingdingusage.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import demo.august1996.top.databingdingusage.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.dataBind2View).setOnClickListener(this);
        findViewById(R.id.viewBind2View).setOnClickListener(this);
        findViewById(R.id.listView).setOnClickListener(this);
        findViewById(R.id.importView).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.dataBind2View:
                intent.setClass(MainActivity.this, DataBind2ViewActivity.class);
                break;
            case R.id.viewBind2View:
                intent.setClass(MainActivity.this, ViewBind2DataAndDataBind2ViewActivity.class);
                break;
            case R.id.listView:
                intent.setClass(MainActivity.this, ListViewActivity.class);
                break;
            case R.id.importView:
                intent.setClass(MainActivity.this, ImportViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
