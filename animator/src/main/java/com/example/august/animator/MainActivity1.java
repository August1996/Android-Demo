package com.example.august.animator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {

    private Button showBtn;
    private Button hideBtn;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        showBtn = (Button) findViewById(R.id.showBtn);
        hideBtn = (Button) findViewById(R.id.hideBtn);
        mLayout = (LinearLayout) findViewById(R.id.mLayout);

        showBtn.setOnClickListener(this);
        hideBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showBtn:
                Utils.showLayout(mLayout, Utils.FROM_RIGHT, 300, new DecelerateInterpolator(),
                        (WindowManager) getSystemService(Context.WINDOW_SERVICE), 0);
                break;
            case R.id.hideBtn:
                Utils.hideLayout(mLayout, 300, new DecelerateInterpolator(),
                        (WindowManager) getSystemService(Context.WINDOW_SERVICE));
                break;
        }
    }
}
