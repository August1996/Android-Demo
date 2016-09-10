package demo.august1996.top.transitionanimationsdemo;

import android.animation.Animator;
import android.graphics.Color;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demo.august1996.top.transitionanimationsdemo.Activity.ToolbarActivity;

public class CircularAnimationsActivity extends ToolbarActivity {


    /**
     * 产生水波纹的View
     */
    TextView target;

    /**
     * Toolbar的那个圆点
     */
    ImageView imageView;

    /**
     * 产生进入动画效果的底部ImageView
     */
    List<View> imageViewList = new ArrayList<>();

    /**
     * 可以产生动画的ViewGroup
     */
    ViewGroup container;

    /**
     * 蓝色点的原始参数，用于位置移动后恢复位置
     */
    RelativeLayout.LayoutParams originlParams;

    @Override
    protected String getToolbarTitle() {
        return "水波纹动画";
    }

    /**
     * 初始化View和动画
     */
    @Override
    protected void initView() {
        container = (ViewGroup) findViewById(R.id.container);
        target = (TextView) findViewById(R.id.target);
        imageView = (ImageView) findViewById(R.id.img1);
        imageViewList.add(findViewById(R.id.circle_orange));
        imageViewList.add(findViewById(R.id.circle_blue));
        imageViewList.add(findViewById(R.id.circle_yellow));
        imageViewList.add(findViewById(R.id.circle_red));

        getWindow().setSharedElementEnterTransition(new ChangeBounds());


        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            /**
             * 当Toobar那个点到达Toolbar的指定个位置后，隐藏Toolbar的点并且在点的的位置产生水波纹动画
             * 同时触发底部ImageView的动画效果
             * @param transition
             */
            @Override
            public void onTransitionEnd(Transition transition) {
                int cx = imageView.getLeft() + imageView.getWidth() / 2;
                int cy = imageView.getTop() + imageView.getHeight() / 2;
                int startR = imageView.getWidth() / 2;
                int endR = Math.max(mToolbar.getWidth() / 2, mToolbar.getHeight() / 2);
                startCircularAnimation(mToolbar, cx, cy, startR, endR, Color.parseColor("#ffffe600"));
                imageView.setVisibility(View.GONE);

                //下面的方式是使每个View的动画产生顺序，时差200ms
                int delay = 200;
                for (int i = 0; i < imageViewList.size(); i++) {
                    View v = imageViewList.get(i);
                    v.animate()
                            .setStartDelay(delay * i)
                            .scaleX(1)
                            .scaleY(1)
                            .start();
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_circular_animations;
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    /**
     * orange   red yellow的原理一样，只是产生动画的坐标XY不一样
     *
     * @param v
     */
    public void orange(View v) {
        int cx = target.getWidth() / 2;
        int cy = target.getHeight() / 2;
        int startR = v.getWidth() / 2;
        int endR = Math.max(target.getWidth() / 2, target.getHeight() / 2);
        startCircularAnimation(target, cx, cy, startR, endR, Color.parseColor("#ffffae00"));
    }

    public void red(View v) {
        int cx = target.getWidth() / 2;
        int cy = mToolbar.getBottom();
        int startR = v.getWidth() / 2;
        int endR = Math.max(target.getWidth() / 2, target.getHeight() / 2);
        startCircularAnimation(target, cx, cy, startR, endR, Color.parseColor("#ffff3d00"));
    }

    public void yellow(View v) {
        int cx = v.getLeft() + v.getWidth() / 2;
        int cy = v.getTop() + v.getHeight() / 2;
        int startR = v.getWidth() / 2;
        int endR = Math.max(target.getWidth() / 2, target.getHeight() / 2);
        startCircularAnimation(target, cx, cy, startR, endR, Color.parseColor("#ffffe600"));
    }

    /**
     * 此处跟上面的区别仅仅是先进行元素动画改变元素的位置
     * 然后再开启水波纹动画
     * 最后位置设置回原来位置
     *
     * @param v
     */
    public void blue(final View v) {
        Transition transition = TransitionInflater.from(CircularAnimationsActivity.this).inflateTransition(R.transition.changebounds_with_arcmotion);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                int cx = target.getWidth() / 2;
                int cy = target.getHeight() / 2;
                int startR = v.getWidth() / 2;
                int endR = Math.max(target.getWidth() / 2, target.getHeight() / 2);
                Animator animator = startCircularAnimation(target, cx, cy, startR, endR, Color.parseColor("#ff0095ff"));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        v.setLayoutParams(originlParams);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        if (originlParams == null) {
            originlParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
        }
        TransitionManager.beginDelayedTransition(container, transition);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        v.setLayoutParams(params);

    }

    /**
     * 开启一个水波纹动画
     *
     * @param target 开启的View
     * @param cx     产生动画的X坐标
     * @param cy     产生动画的Y坐标
     * @param startR 水波纹起始半径
     * @param endR   水波纹扩散半径
     * @param color  水波纹颜色
     * @return
     */
    private Animator startCircularAnimation(final View target, int cx, int cy, int startR, int endR, final int color) {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(target, cx, cy, startR, endR);
        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                target.setBackgroundColor(color);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        circularReveal.start();
        return circularReveal;
    }
}
