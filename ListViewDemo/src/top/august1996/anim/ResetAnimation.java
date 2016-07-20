package top.august1996.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

public class ResetAnimation extends Animation {
	
	private View	view;
	private int		startHeight;
	private int		endHeight;
					
	public ResetAnimation(View view, int startHeight, int endHeight, int duration, Interpolator interpolator) {
		this.startHeight = startHeight;
		this.endHeight = endHeight;
		this.view = view;
		setDuration(duration);
		if (interpolator != null) {
			setInterpolator(interpolator);
		} else {
			setInterpolator(new OvershootInterpolator(2));
		}
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		
		Integer newHeight = evaluate(interpolatedTime, startHeight, endHeight);
		view.getLayoutParams().height = newHeight;
		view.requestLayout();
		
		super.applyTransformation(interpolatedTime, t);
	}
	
	private Integer evaluate(float fraction, Integer startValue, Integer endValue) {
		int startInt = startValue;
		return (int) (startInt + fraction * (endValue - startInt));
	}
}