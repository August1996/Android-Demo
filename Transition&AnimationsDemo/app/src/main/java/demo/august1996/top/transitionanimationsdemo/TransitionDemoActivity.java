package demo.august1996.top.transitionanimationsdemo;

import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import demo.august1996.top.transitionanimationsdemo.Activity.ToolbarActivity;

public class TransitionDemoActivity extends ToolbarActivity {


    @Override
    protected void beforeInitView() {
        super.beforeInitView();
        Transition transition = null;
        switch (getIntent().getIntExtra("type", -1)) {
            case TransitionActivity.EXPLODE_CODE:
                transition = new Explode();
                transition.setDuration(1000);
                transition.setInterpolator(new DecelerateInterpolator());
                break;
            case TransitionActivity.EXPLODE_XML:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.simple_explode);
                break;
            case TransitionActivity.SLIDE_CODE:
                transition = new Slide();
                ((Slide) transition).setSlideEdge(Gravity.RIGHT);
                transition.setDuration(1000);
                transition.setInterpolator(new DecelerateInterpolator());
                break;
            case TransitionActivity.SLIDE_XML:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.simple_slide);
                break;
        }
        if (transition != null) {
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);
        }
    }

    @Override
    protected String getToolbarTitle() {
        return "转场动画看这里";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_transition_demo;
    }

    public void exit(View v) {
        finishAfterTransition();
    }

    @Override
    protected boolean canBack() {
        return true;
    }
}
