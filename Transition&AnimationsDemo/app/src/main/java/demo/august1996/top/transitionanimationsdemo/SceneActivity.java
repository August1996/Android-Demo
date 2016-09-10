package demo.august1996.top.transitionanimationsdemo;

import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;
import java.util.List;

import demo.august1996.top.transitionanimationsdemo.Activity.ToolbarActivity;

public class SceneActivity extends ToolbarActivity {


    /**
     * 用于动画效果的按钮
     */
    List<View> buttonList = new ArrayList<>();

    /**
     * 添加场景到该ViewGroup
     */
    ViewGroup viewRoot;

    /**
     * 一共5个场景，0为默认场景
     */
    Scene scene0;
    Scene scene1;
    Scene scene2;
    Scene scene3;
    Scene scene4;

    @Override
    protected void beforeInitView() {
        super.beforeInitView();
    }

    @Override
    protected String getToolbarTitle() {
        return "场景动画";
    }

    @Override
    protected void initView() {
        /**
         * 初始化按钮View
         */
        buttonList.add(findViewById(R.id.one));
        buttonList.add(findViewById(R.id.two));
        buttonList.add(findViewById(R.id.three));
        buttonList.add(findViewById(R.id.four));
        viewRoot = (ViewGroup) findViewById(R.id.viewRoot);

        /**
         * 初始化场景
         */
        scene0 = Scene.getSceneForLayout(viewRoot, R.layout.sences_layout_0, this);
        scene1 = Scene.getSceneForLayout(viewRoot, R.layout.sences_layout_1, this);
        scene2 = Scene.getSceneForLayout(viewRoot, R.layout.sences_layout_2, this);
        scene3 = Scene.getSceneForLayout(viewRoot, R.layout.sences_layout_3, this);
        scene4 = Scene.getSceneForLayout(viewRoot, R.layout.sences_layout_4, this);

        /**
         * 当场景1被切换的时候，按钮显示动画
         */
        scene0.setEnterAction(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < buttonList.size(); i++) {
                    View view = buttonList.get(i);
                    view.animate()
                            .setDuration(1000)
                            .scaleX(1)
                            .scaleY(1)
                            .alpha(1)
                            .start();
                }
            }
        });

        /**
         * 切换到场景0
         */
        TransitionManager.go(scene0);
    }

    /**
     * 使用简单动画切换场景1
     * @param v
     */
    public void one(View v) {
        TransitionManager.go(scene1, new ChangeBounds());
    }

    /**
     * 使用混合动画加载场景2
     * @param v
     */
    public void two(View v) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setDuration(1000);
        Slide slide = new Slide();
        ChangeBounds changeBounds = new ChangeBounds();
        transitionSet.addTransition(slide);
        transitionSet.addTransition(changeBounds);
        TransitionManager.go(scene2, transitionSet);
    }

    /**
     * 使用混合动画加载场景3，并设置动画顺序
     * @param v
     */
    public void three(View v) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setDuration(1000);
        Slide slide = new Slide();
        ChangeBounds changeBounds = new ChangeBounds();
        transitionSet.addTransition(slide);
        transitionSet.addTransition(changeBounds);
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        TransitionManager.go(scene3, transitionSet);
    }

    /**
     * 使用混合动画加载场景4，并设置动画顺序和差时器
     * @param v
     */
    public void four(View v) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setDuration(1000);
        transitionSet.setInterpolator(new BounceInterpolator());
        Slide slide = new Slide();
        ChangeBounds changeBounds = new ChangeBounds();
        transitionSet.addTransition(slide);
        transitionSet.addTransition(changeBounds);
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        TransitionManager.go(scene4, transitionSet);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_scene;
    }

    @Override
    protected boolean canBack() {
        return true;
    }
}
