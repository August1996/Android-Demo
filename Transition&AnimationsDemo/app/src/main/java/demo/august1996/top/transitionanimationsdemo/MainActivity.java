package demo.august1996.top.transitionanimationsdemo;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import demo.august1996.top.transitionanimationsdemo.Activity.ToolbarActivity;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void beforeInitView() {
        super.beforeInitView();

        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(1000);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);

    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected String getToolbarTitle() {
        return "谁说Android的动画不廉价";
    }

    public void transition(View v) {
        Intent intent = new Intent(this, TransitionActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    public void shareElements(View v) {

        ImageView imageView = (ImageView) findViewById(R.id.img);
        Intent intent = new Intent(this, ShareElementsActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair<View, String>(imageView, imageView.getTransitionName()));
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    public void viewAnimations(View v) {
        Intent intent = new Intent(this, ViewAnimationActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    public void circularAnimations(View v) {
        View view = findViewById(R.id.img1);
        /**
         * view是用于共享元素toolbar上面那一点
         * 希望达到从共享元素扩散的效果
         */
        Intent intent = new Intent(this, CircularAnimationsActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair(view, view.getTransitionName()));
        startActivity(intent, activityOptionsCompat.toBundle());
    }
}
