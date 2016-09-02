package top.august1996.ui;

import com.example.swipelayoutdemo.R;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SwipeLayout extends FrameLayout implements OnClickListener {
	
	public enum Status {
		CLOSE, OPEN, DRAGING
	}
	
	public interface OnDrawStatusChangeListener {
		void onOpen();
		
		void onDraging(int dy);
		
		void onClose();
	}
	
	public OnDrawStatusChangeListener	mOnDrawStatusChangeListener;
	
	private Status						status		= Status.CLOSE;
	
	private TextView					leftBtn;
	private TextView					rightBtn;
	
	private ViewDragHelper				mViewDragHelper;
	private View						mFrontView;
	private View						mBackView;
	private Callback					mCallback	= new Callback() {
														
														@Override
														public boolean tryCaptureView(View arg0, int arg1) {
															return true;
														}
														
														@Override
														public int clampViewPositionHorizontal(View child, int left,
																int dx) {
															return left;
														}
														
														public void onViewPositionChanged(View changedView, int left,
																int top, int dx, int dy) {
															
															if (mBackView.getLeft() <= -mFrontView.getWidth()) {
																status = Status.OPEN;
																if (mOnDrawStatusChangeListener != null) {
																	mOnDrawStatusChangeListener.onOpen();
																}
															} else if (mBackView.getLeft() >= 0) {
																status = Status.CLOSE;
																if (mOnDrawStatusChangeListener != null) {
																	mOnDrawStatusChangeListener.onClose();
																}
															} else {
																status = Status.DRAGING;
																if (mOnDrawStatusChangeListener != null) {
																	mOnDrawStatusChangeListener.onDraging(dy);
																}
															}
															
															if ((mBackView.getLeft() <= -mFrontView.getWidth()
																	&& dx < 0)
																	|| (mFrontView.getLeft() <= mBackView.getWidth()
																			- mFrontView.getWidth())
																	|| (mBackView.getLeft() >= 0 && dx > 0)) {
																
																changedView.offsetLeftAndRight(-dx);
																return;
															}
															
															if (changedView == mBackView) {
																mFrontView.offsetLeftAndRight(dx);
															} else if (changedView == mFrontView) {
																mBackView.offsetLeftAndRight(dx);
															}
															invalidate();
															
														};
														
														public void onViewReleased(View releasedChild, float xvel,
																float yvel) {
															
															if (Math.abs(mBackView.getLeft()) >= mFrontView.getWidth()
																	/ 2
																	&& mViewDragHelper.smoothSlideViewTo(mBackView,
																			-mFrontView.getWidth(), 0)) {
																ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
																;
															} else if (mViewDragHelper.smoothSlideViewTo(mBackView, 0,
																	0)) {
																ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
															}
														};
													};
	
	public SwipeLayout(Context context) {
		this(context, null);
	}
	
	public SwipeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mViewDragHelper.shouldInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mViewDragHelper.processTouchEvent(event);
		return true;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		layoutContent(false);
		
	}
	
	private void layoutContent(boolean isOpen) {
		if (isOpen) {
			mFrontView.layout(mBackView.getWidth() - mFrontView.getWidth(), 0, mBackView.getWidth(),
					mFrontView.getHeight());
			mBackView.layout(-mFrontView.getWidth(), 0, mBackView.getWidth(), mBackView.getHeight());
		} else {
			mBackView.layout(0, 0, mBackView.getWidth(), mBackView.getHeight());
			mFrontView.layout(mBackView.getWidth(), 0, mBackView.getWidth() + mFrontView.getWidth(),
					mFrontView.getHeight());
		}
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		mBackView = getChildAt(0);
		mFrontView = getChildAt(1);
		rightBtn = (TextView) findViewById(R.id.rightBtn);
		leftBtn = (TextView) findViewById(R.id.leftBtn);
		rightBtn.setOnClickListener(this);
		leftBtn.setOnClickListener(this);
	}
	
	private OnButtonClickListener mOnButtonClickListener;
	
	public void setOnButtonClickListener(OnButtonClickListener listener) {
		mOnButtonClickListener = listener;
	}
	
	public interface OnButtonClickListener {
		void onRightClick();
		
		void onLeftClick();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rightBtn:
				if (mOnButtonClickListener != null) {
					mOnButtonClickListener.onRightClick();
				}
				break;
			case R.id.leftBtn:
				if (mOnButtonClickListener != null) {
					mOnButtonClickListener.onLeftClick();
				}
				break;
			default:
				break;
		}
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (mViewDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
}
