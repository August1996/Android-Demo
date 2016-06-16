package com.example.august.sortkeylist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by August on 16/5/11.
 */
public class SortListView extends ViewGroup {

    /**
     * 这是索引的26个字母
     */
    private String[] alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"
            , "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private OnTouchListener mOnTouchListener;

    public interface OnTouchListener {
        public void onTouch(String s);
    }

    /**
     * 上面是我们的回调接口和当前对象持有的回调
     */


    public SortListView(Context context) {
        super(context);
    }

    public void setmOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public SortListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SortListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /**
         * 我们去截取按下和移动的事件,通过点击的Y坐标我们可以轻易计算出点下的位置是对应哪个字母的索引
         * 计算是根据公式 当前点击的Y坐标/当前View的总高度 = 字母索引/所有字母长度
         * 然后把字母作为参数给传给回调
         */
        if (mOnTouchListener != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_MOVE) {
                int index = (int) (event.getY() / (getHeight() / alphabet.length));
                if (index > -1 && index < alphabet.length) {
                    mOnTouchListener.onTouch(alphabet[index]);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.parseColor("#bebebe"));
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.WHITE);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
        int eachHeight = getHeight() / alphabet.length;
        int width = getWidth();
        for (int i = 0; i < alphabet.length; i++) {
            float x = width / 2 - mPaint.measureText(alphabet[i]) / 2;
            canvas.drawText(alphabet[i], x, eachHeight * (i + 1), mPaint);
        }

        /**
         * 每个字母占据的高度是当前 父容器的高度/字母个数
         * 然后我们可以把每个字母给画出来
         */
    }
}
