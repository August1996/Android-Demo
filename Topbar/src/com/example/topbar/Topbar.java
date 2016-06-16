package com.example.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Topbar extends RelativeLayout {

	private Button leftButton;
	private TextView titleView;
	private Button rightButton;

	private LayoutParams leftParams, rightParams, titleParams;

	private int rightTextColor;
	private float rightTextSize;
	private String rightText;
	private int rightBackgroundColor;

	private int leftTextColor;
	private float leftTextSize;
	private String leftText;
	private int leftBackgroundColor;

	private int titleTextColor;
	private float titleTextSize;
	private String titleText;
	private int titleBackgroundColor;

	private int backgroundColor;

	TopBarOnClickListener listener;

	public Topbar(final Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

		leftTextColor = ta.getColor(R.styleable.Topbar_leftTextColor, 0);
		leftTextSize = Topbar.px2sp(context, ta.getDimension(R.styleable.Topbar_leftTextSize, 16));
		leftText = ta.getString(R.styleable.Topbar_leftText);
		leftBackgroundColor = ta.getColor(R.styleable.Topbar_leftBackgroundColor, 0);

		rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor, 0);
		rightTextSize = Topbar.px2sp(context, ta.getDimension(R.styleable.Topbar_rightTextSize, 16));
		rightText = ta.getString(R.styleable.Topbar_rightText);
		rightBackgroundColor = ta.getColor(R.styleable.Topbar_rightBackgroundColor, 0);

		titleText = ta.getString(R.styleable.Topbar_title);
		titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor, 0);
		titleTextSize = Topbar.px2sp(context, ta.getDimension(R.styleable.Topbar_titleTextSize, 16));
		titleBackgroundColor = ta.getColor(R.styleable.Topbar_titleBackgroundColor, 0);

		backgroundColor = ta.getColor(R.styleable.Topbar_backgroundColor, 0);
		ta.recycle();

		leftButton = new Button(context);
		rightButton = new Button(context);
		titleView = new TextView(context);

		leftButton.setTextColor(leftTextColor);
		leftButton.setTextSize(leftTextSize);
		leftButton.setBackgroundColor(leftBackgroundColor);
		leftButton.setText(leftText);

		rightButton.setTextColor(rightTextColor);
		rightButton.setTextSize(rightTextSize);
		rightButton.setBackgroundColor(rightBackgroundColor);
		rightButton.setText(rightText);

		titleView.setText(titleText);
		titleView.setTextSize(titleTextSize);
		titleView.setTextColor(titleTextColor);
		titleView.setGravity(Gravity.CENTER);
		titleView.setBackgroundColor(titleBackgroundColor);

		setBackgroundColor(backgroundColor);

		leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);

		rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);

		titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

		addView(leftButton, leftParams);
		addView(rightButton, rightParams);
		addView(titleView, titleParams);

		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.leftClick();
				}
			}
		});

		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.rightClick();
				}
			}
		});
	}

	public interface TopBarOnClickListener {
		public void leftClick();

		public void rightClick();
	}

	public void setOnClickListener(TopBarOnClickListener listener) {
		this.listener = listener;
	}

	public void setLeftIsVisible(boolean flag) {
		if (flag) {
			leftButton.setVisibility(View.VISIBLE);
		} else {
			leftButton.setVisibility(View.INVISIBLE);
		}
	}

	public void setRightIsVisible(boolean flag) {
		if (flag) {
			rightButton.setVisibility(View.VISIBLE);
		} else {
			rightButton.setVisibility(View.INVISIBLE);
		}
	}

	public void setTitleText(String text) {
		titleView.setText(text);
	}

	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
}
