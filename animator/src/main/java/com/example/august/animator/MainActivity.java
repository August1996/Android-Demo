package com.example.august.animator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView targetTV;
    Button rotateBtn;
    Button translateBtn;
    Button scaleBtn;
    Button alphaBtn;
    Button mixBtn;

    RotateAnimation rotateAnimation;
    TranslateAnimation translateAnimation;
    ScaleAnimation scaleAnimation;
    AlphaAnimation alphaAnimation;
    AnimationSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initViews();
        rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        translateAnimation = new TranslateAnimation(0, 0, 100, 100);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);
        scaleAnimation = new ScaleAnimation(0, 300, 0, 300);
        scaleAnimation.setDuration(1000);
        alphaAnimation = new AlphaAnimation(1, 0.2f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        set = new AnimationSet(true);
        set.addAnimation(rotateAnimation);
        set.addAnimation(translateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
    }

    private void initViews() {
        targetTV = (TextView) findViewById(R.id.targetTV);
        rotateBtn = (Button) findViewById(R.id.rotateBtn);
        translateBtn = (Button) findViewById(R.id.translateBtn);
        scaleBtn = (Button) findViewById(R.id.scaleBtn);
        alphaBtn = (Button) findViewById(R.id.alphaBtn);
        mixBtn = (Button) findViewById(R.id.mixBtn);
        rotateBtn.setOnClickListener(this);
        translateBtn.setOnClickListener(this);
        scaleBtn.setOnClickListener(this);
        alphaBtn.setOnClickListener(this);
        mixBtn.setOnClickListener(this);
        targetTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rotateBtn:
                targetTV.startAnimation(rotateAnimation);
                break;
            case R.id.scaleBtn:
                targetTV.startAnimation(scaleAnimation);
                break;
            case R.id.alphaBtn:
                targetTV.startAnimation(alphaAnimation);
                break;
            case R.id.translateBtn:
                targetTV.startAnimation(translateAnimation);
                break;
            case R.id.mixBtn:
                targetTV.startAnimation(set);
                break;
            case R.id.targetTV:
                Toast.makeText(MainActivity.this, "targetTV clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
