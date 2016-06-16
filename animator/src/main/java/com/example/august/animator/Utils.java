package com.example.august.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;

/**
 * Created by August on 16/4/12.
 */
public class Utils {
    public static final int FROM_TOP = 0;
    public static final int FROM_LEFT = 1;
    public static final int FROM_RIGHT = 2;
    public static final int TO_TOP = 0;
    public static final int TO_LEFT = 1;
    public static final int TO_RIGHT = 2;

    public static void showLayout(View v, int direction, int time, Interpolator interpolator, WindowManager wm,
                                  int offset) {
        v.setVisibility(v.VISIBLE);
        v.setTag(-1, direction);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
        ObjectAnimator translateAnimator = null;
        switch (direction) {
            case FROM_TOP:
                translateAnimator = ObjectAnimator.ofFloat(v, "translationY", -v.getHeight(), (float) (0 + offset));
                break;
            case FROM_LEFT:
                translateAnimator = ObjectAnimator.ofFloat(v, "translationX", -v.getWidth(), (float) (0 + offset));
                break;
            case FROM_RIGHT:
                translateAnimator = ObjectAnimator.ofFloat(v, "translationX", screenWidth + v.getWidth(),
                        screenWidth - v.getWidth() + (float) offset);
                break;
        }

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(interpolator);
        set.setDuration(time);
        set.playTogether(alphaAnimator, translateAnimator);
        set.playSequentially();
        set.start();
    }

    public static void hideLayout(final View v, int time, Interpolator interpolator, WindowManager wm) {
        int direction = 1;
        direction = (int) v.getTag(-1);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(v, "alpha", 1, 0);
        ObjectAnimator translateAnimator = null;
        switch (direction) {
            case TO_TOP:
                translateAnimator = ObjectAnimator.ofFloat(v, "translationY", v.getTop(), -v.getHeight());
                break;
            case TO_LEFT:
                translateAnimator = ObjectAnimator.ofFloat(v, "translationX", v.getLeft(), -v.getWidth());
                break;
            case TO_RIGHT:
                translateAnimator = ObjectAnimator.ofFloat(v, "translationX", v.getLeft(), screenWidth + v.getWidth());
                break;
        }


        AnimatorSet set = new AnimatorSet();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);
                v.invalidate(0, 0, v.getWidth(), v.getHeight());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        set.setInterpolator(interpolator);
        set.setDuration(time);
        set.playTogether(alphaAnimator, translateAnimator);
        set.start();
    }

}
