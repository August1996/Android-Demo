package demo.august1996.top.transitionanimationsdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        beforeInitView();
        initView();
        initData();
    }

    protected void beforeInitView() {

    }

    protected abstract void initView();


    protected void initData() {

    }


    protected abstract int getContentViewID();
}
