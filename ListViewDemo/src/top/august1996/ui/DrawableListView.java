package top.august1996.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.listviewdemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import top.august1996.anim.ResetAnimation;


/**
 * 自定义刷新与加载更多控件
 * @author August
 *
 */
public class DrawableListView extends ListView {
	/**
	 * 刷新时间显示格式
	 */
	private SimpleDateFormat	sdf					= new SimpleDateFormat("yyyy-MM-dd HH:mm");
													
	/**
	 * 刷新头部相关 headerDrawRefreshY 下拉多少允许刷新 headerDrawMaxY 下拉的最大高度
	 */
	private int					headerDrawRefreshY	= dp2px(80);
	private int					headerDrawMaxY		= dp2px(150);
	private View				headerContentView;
								
	private ImageView			headerArrowIMV;
	private ImageView			headerWaitIMV;
	private TextView			headerTipTV;
	private TextView			headerLastTimeTV;
	private ImageView			footerArrowIMV;
	private ImageView			footerWaitIMV;
	private TextView			footerTipTV;
	private TextView			footerLastTimeTV;
	private RefreshListener		mRefreshListener;
	private ListViewState		state				= ListViewState.NORMAL;
													
	/**
	 * 加载更多头部 : 与刷新头部类似
	 */
	private int					footerDrawRefreshY	= dp2px(80);
	private int					footerDrawMaxY		= dp2px(150);
	private View				footerContentView;
								
	private OnLoadListener		mOnLoadListener;
	private Interpolator		interpolator		= new AccelerateDecelerateInterpolator();
	private int					animationDuration	= 200;
	private ScrollType			scrollType			= ScrollType.NONE;
	private float				mStartY;
								
	private RotateAnimation		rotateAnimation;
								
	public void setRefreshListener(RefreshListener listener) {
		mRefreshListener = listener;
	}
	
	public void setOnLoadListener(OnLoadListener listener) {
		mOnLoadListener = listener;
	}
	
	/**
	 * 外部调用,刷新完毕
	 */
	public void finishRefresh() {
		setState(ListViewState.NORMAL);
		headerLastTimeTV.setText(sdf.format(new Date()));
		startAnimation(new ResetAnimation(headerContentView, headerContentView.getLayoutParams().height, 0,
				animationDuration, interpolator));
	}
	
	/**
	 * 外部调用,加载完毕
	 */
	public void finishLoad() {
		setState(ListViewState.NORMAL);
		footerLastTimeTV.setText(sdf.format(new Date()));
		startAnimation(new ResetAnimation(footerContentView, footerContentView.getLayoutParams().height, 0,
				animationDuration, interpolator));
	}
	
	/**
	 * 当前ListView的状态 NORMAL 正常状态 HEADER_DRAWING 刷新头部被拉出,但未达到刷新的高度 HEADER_READY
	 * 松开手就可以刷新的状态 HEADER_REFRESHING 正在刷新
	 * 
	 * FOOTER_XXX 同上
	 * 
	 * @author August
	 * 		
	 */
	public enum ListViewState {
		NORMAL, HEADER_DRAWING, HEADER_READY, FOOTER_DRAWING, FOOTER_READY, HEADER_REFRESHING, FOOTER_LOADING
	}
	
	/**
	 * 判断是头部还是底部被拉出
	 * 
	 * @author August
	 * 		
	 */
	public enum ScrollType {
		NONE, HEADER, FOOTER
	}
	
	public interface RefreshListener {
		void onRefresh();
	}
	
	public interface OnLoadListener {
		void onLoad();
		
	}
	
	public DrawableListView(Context context) {
		this(context, null);
	}
	
	public DrawableListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public DrawableListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		initHeaderView(context);
		
		initFooterView(context);
		
		initAnimation();
	}
	
	private void initAnimation() {
		rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(300);
		rotateAnimation.setInterpolator(new LinearInterpolator());
		rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
	}
	
	private void initFooterView(Context context) {
		View footerView = LayoutInflater.from(context).inflate(R.layout.footer_view, null, false);
		footerContentView = footerView.findViewById(R.id.headerContentLL);
		footerArrowIMV = (ImageView) footerView.findViewById(R.id.arrowIMV);
		footerWaitIMV = (ImageView) footerView.findViewById(R.id.waitIMV);
		footerTipTV = (TextView) footerView.findViewById(R.id.tipTV);
		footerLastTimeTV = (TextView) footerView.findViewById(R.id.lastTimeTV);
		setViewHeight(footerContentView, 0);
		addFooterView(footerView);
	}
	
	private void initHeaderView(Context context) {
		View headerView = LayoutInflater.from(context).inflate(R.layout.header_view, null, false);
		headerContentView = headerView.findViewById(R.id.headerContentLL);
		headerArrowIMV = (ImageView) headerView.findViewById(R.id.arrowIMV);
		headerWaitIMV = (ImageView) headerView.findViewById(R.id.waitIMV);
		headerTipTV = (TextView) headerView.findViewById(R.id.tipTV);
		headerLastTimeTV = (TextView) headerView.findViewById(R.id.lastTimeTV);
		setViewHeight(headerContentView, 0);
		addHeaderView(headerView);
	}
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
			
		if (isTouchEvent && state != ListViewState.HEADER_REFRESHING && state != ListViewState.FOOTER_LOADING) {
			if ((deltaY < 0 && headerContentView.getLayoutParams().height < headerDrawMaxY)
					&& mRefreshListener != null) {
				// 上拉
				scrollType = ScrollType.HEADER;
				setViewHeight(headerContentView, headerContentView.getLayoutParams().height + Math.abs(deltaY));
				if (headerContentView.getLayoutParams().height >= headerDrawRefreshY) {
					setState(ListViewState.HEADER_READY);
				} else {
					setState(ListViewState.HEADER_DRAWING);
				}
			} else if ((deltaY > 0 && footerContentView.getLayoutParams().height < footerDrawMaxY)
					&& mOnLoadListener != null) {
				// 下拉
				scrollType = ScrollType.FOOTER;
				setViewHeight(footerContentView, footerContentView.getLayoutParams().height + Math.abs(deltaY));
				if (footerContentView.getLayoutParams().height >= footerDrawRefreshY) {
					setState(ListViewState.FOOTER_READY);
				} else {
					setState(ListViewState.FOOTER_DRAWING);
				}
			}
			
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
				maxOverScrollY, isTouchEvent);
	}
	
	private int dp2px(int dp) {
		int px = (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
		return px;
	}
	
	private void setViewHeight(View view, int height) {
		if (view != null && headerContentView.getLayoutParams() != null) {
			view.getLayoutParams().height = height;
			view.requestLayout();
		}
	}
	
	public void setState(ListViewState state) {
		this.state = state;
		switch (state) {
			case NORMAL:
				headerTipTV.setText("下拉刷新");
				headerArrowIMV.setVisibility(View.VISIBLE);
				headerWaitIMV.setVisibility(View.GONE);
				headerWaitIMV.clearAnimation();
				footerTipTV.setText("上拉加载更多");
				footerArrowIMV.setVisibility(View.VISIBLE);
				footerWaitIMV.setVisibility(View.GONE);
				footerWaitIMV.clearAnimation();
				break;
			case HEADER_DRAWING:
				headerTipTV.setText("下拉刷新");
				headerWaitIMV.clearAnimation();
				headerArrowIMV.setVisibility(View.VISIBLE);
				headerWaitIMV.setVisibility(View.GONE);
				break;
			case HEADER_READY:
				headerTipTV.setText("松开刷新");
				headerWaitIMV.clearAnimation();
				headerArrowIMV.setVisibility(View.VISIBLE);
				headerWaitIMV.setVisibility(View.GONE);
				break;
			case HEADER_REFRESHING:
				headerTipTV.setText("正在刷新...");
				headerWaitIMV.startAnimation(rotateAnimation);
				headerArrowIMV.setVisibility(View.GONE);
				headerWaitIMV.setVisibility(View.VISIBLE);
				break;
			case FOOTER_DRAWING:
				footerTipTV.setText("上拉加载更多");
				footerWaitIMV.clearAnimation();
				footerArrowIMV.setVisibility(View.VISIBLE);
				footerWaitIMV.setVisibility(View.GONE);
				break;
			case FOOTER_READY:
				footerTipTV.setText("松开加载更多");
				footerWaitIMV.clearAnimation();
				footerArrowIMV.setVisibility(View.VISIBLE);
				footerWaitIMV.setVisibility(View.GONE);
				break;
			case FOOTER_LOADING:
				footerTipTV.setText("正在加载...");
				footerWaitIMV.startAnimation(rotateAnimation);
				footerArrowIMV.setVisibility(View.GONE);
				footerWaitIMV.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			mStartY = ev.getY();
		} else if (ev.getAction() == MotionEvent.ACTION_UP) {
			scrollType = ScrollType.NONE;
			if (state == ListViewState.HEADER_DRAWING) {
				startAnimation(new ResetAnimation(headerContentView, headerContentView.getLayoutParams().height, 0,
						animationDuration, interpolator));
				return true;
			} else if (state == ListViewState.HEADER_READY) {
				startAnimation(new ResetAnimation(headerContentView, headerContentView.getLayoutParams().height,
						headerDrawRefreshY, animationDuration, interpolator));
				mRefreshListener.onRefresh();
				setState(ListViewState.HEADER_REFRESHING);
				return true;
			} else if (state == ListViewState.FOOTER_DRAWING) {
				startAnimation(new ResetAnimation(footerContentView, footerContentView.getLayoutParams().height, 0,
						animationDuration, interpolator));
				return true;
			} else if (state == ListViewState.FOOTER_READY) {
				startAnimation(new ResetAnimation(footerContentView, footerContentView.getLayoutParams().height,
						footerDrawRefreshY, animationDuration, interpolator));
				mOnLoadListener.onLoad();
				setState(ListViewState.FOOTER_LOADING);
			}
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			
			if (state != ListViewState.FOOTER_LOADING && state != ListViewState.HEADER_REFRESHING) {
				if (headerContentView.getLayoutParams().height <= 0
						&& footerContentView.getLayoutParams().height <= 0) {
					scrollType = ScrollType.NONE;
					return super.onTouchEvent(ev);
				}
				
				float dY = ev.getY() - mStartY;
				
				if (dY > 0 && scrollType == ScrollType.FOOTER) {
					setViewHeight(footerContentView, (int) (footerContentView.getLayoutParams().height - Math.abs(dY)));
					if (footerContentView.getLayoutParams().height >= footerDrawRefreshY) {
						setState(ListViewState.FOOTER_READY);
					} else {
						setState(ListViewState.FOOTER_DRAWING);
					}
				} else if (dY < 0 && scrollType == ScrollType.HEADER) {
					setViewHeight(headerContentView, (int) (headerContentView.getLayoutParams().height - Math.abs(dY)));
					if (headerContentView.getLayoutParams().height >= headerDrawRefreshY) {
						setState(ListViewState.HEADER_READY);
					} else {
						setState(ListViewState.HEADER_DRAWING);
					}
				}
				mStartY = ev.getY();
			}
		}
		
		return super.onTouchEvent(ev);
	}
	
}
