package top.august1996.ui;

import com.example.listviewdemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

public class ImageListView extends ListView {
	
	private ImageView	headerImageView;
	private int			headerHeight	= dp2px(180);
	private int			headerMaxHeight	= dp2px(350);
	private int			duration		= 500;
										
	public ImageListView(Context context) {
		this(context, null);
	}
	
	public ImageListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ImageListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		View mHeaderView = LayoutInflater.from(context).inflate(R.layout.image_header_view, null, false);
		headerImageView = (ImageView) mHeaderView.findViewById(R.id.headerImageView);
		headerImageView.getLayoutParams().height = headerHeight;
		addHeaderView(mHeaderView);
	}
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
			
		if (deltaY < 0 && isTouchEvent && headerImageView.getLayoutParams().height < headerMaxHeight) {
			headerImageView.getLayoutParams().height += Math.abs(deltaY);
			headerImageView.requestLayout();
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
				maxOverScrollY, isTouchEvent);
	}
	
	private int dp2px(int dp) {
		int px = (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
		return px;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			startAnimation(new ResetAnimation(headerImageView.getHeight(), headerHeight));
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	class ResetAnimation extends Animation {
		
		private int	startHeight;
		private int	endHeight;
					
		ResetAnimation(int startHeight, int endHeight) {
			this.startHeight = startHeight;
			this.endHeight = endHeight;
			setDuration(duration);
			setInterpolator(new OvershootInterpolator(2));
		}
		
		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			
			Integer newHeight = evaluate(interpolatedTime, startHeight, endHeight);
			headerImageView.getLayoutParams().height = newHeight;
			headerImageView.requestLayout();
			
			super.applyTransformation(interpolatedTime, t);
		}
		
		private Integer evaluate(float fraction, Integer startValue, Integer endValue) {
			int startInt = startValue;
			return (int) (startInt + fraction * (endValue - startInt));
		}
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setImageBitmap(Bitmap bm) {
		headerImageView.setImageBitmap(bm);
	}
	
	public void setImageResource(int resID) {
		headerImageView.setImageResource(resID);
	}
	
	public void setHeaderHeight(int headerHeight) {
		this.headerHeight = headerHeight;
	}
	
	public void setHeaderMaxHeight(int headerMaxHeight) {
		this.headerMaxHeight = headerMaxHeight;
	}
	
}
