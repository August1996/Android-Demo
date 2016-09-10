package demo.august1996.top.transitionanimationsdemo;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import demo.august1996.top.transitionanimationsdemo.Activity.ToolbarActivity;

public class ViewAnimationActivity extends ToolbarActivity {

    ViewGroup container;
    View imageView;
    boolean isChangeSize = false;
    boolean isChangePosition = false;

    @Override
    protected String getToolbarTitle() {
        return "元素动画";
    }

    @Override
    protected void initView() {
        imageView = findViewById(R.id.img);
        container = (ViewGroup) findViewById(R.id.container);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_view_animation;
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    /**
     * 修改指定View的大小，根据isChangeSize来判断
     * isChangeSize == true：为修改过的大小，大小*2
     * isChangeSize == false：为原始的大小，大小/2
     * @param v
     */
    public void changeSize(View v) {
        TransitionManager.beginDelayedTransition(container);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        if (isChangeSize) {
            layoutParams.width = layoutParams.width * 2;
        } else {
            layoutParams.width = layoutParams.width / 2;
        }
        layoutParams.height = layoutParams.width;
        imageView.setLayoutParams(layoutParams);
        isChangeSize = !isChangeSize;
    }

    /**
     * 与changeSize类似
     * @param v
     */
    public void changePosition(View v) {
        TransitionManager.beginDelayedTransition(container);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        if (isChangePosition) {
            layoutParams.gravity = Gravity.CENTER;
        } else {
            layoutParams.gravity = Gravity.LEFT;
        }
        imageView.setLayoutParams(layoutParams);
        imageView.postInvalidate();
        isChangePosition = !isChangePosition;

    }

    public void next(View v) {
        Intent intent = new Intent(this, SceneActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptionsCompat.toBundle());
    }
}
