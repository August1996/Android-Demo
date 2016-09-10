package demo.august1996.top.transitionanimationsdemo;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import demo.august1996.top.transitionanimationsdemo.Activity.ToolbarActivity;

public class TransitionActivity extends ToolbarActivity {

    public static final int EXPLODE_CODE = 1;
    public static final int EXPLODE_XML = 2;
    public static final int SLIDE_CODE = 3;
    public static final int SLIDE_XML = 4;


    @Override
    protected void beforeInitView() {
        super.beforeInitView();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_transition;
    }

    @Override
    protected String getToolbarTitle() {
        return "转场动画";
    }

    public void explodeCode(View v) {
        Intent intent = new Intent(this, TransitionDemoActivity.class);
        intent.putExtra("type", EXPLODE_CODE);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    public void explodeXML(View v) {
        Intent intent = new Intent(this, TransitionDemoActivity.class);
        intent.putExtra("type", EXPLODE_XML);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    public void slideCode(View v) {
        Intent intent = new Intent(this, TransitionDemoActivity.class);
        intent.putExtra("type", SLIDE_CODE);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    public void slideXML(View v) {
        Intent intent = new Intent(this, TransitionDemoActivity.class);
        intent.putExtra("type", SLIDE_XML);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    public void exit(View v) {
        finishAfterTransition();
    }

    @Override
    protected boolean canBack() {
        return true;
    }
}
