package demo.august1996.top.timelineviewdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by August on 16/8/20.
 */
public class TimeLineView extends LinearLayout {

    //高亮图片
    private Bitmap mIcon;

    //时间线左右边距
    private int leftAndRightMargin = dp2px(30);

    //第一个点的坐标
    private int firstX;
    private int firstY;


    //最后一个点的坐标
    private int lastX;
    private int lastY;

    //时间线画笔、颜色、粗细
    private Paint linePaint;
    private int lineColor = Color.parseColor("#ff4ed6b4");
    private int lineStoke = dp2px(2);

    //时间点画笔
    private Paint pointPaint;
    private int pointColor = Color.parseColor("#ff4ed6b4");
    private int pointRadius = dp2px(5);


    //提供当前位置是否为高亮状态
    private HeightLineProvider mHeightLineProvider;

    public interface HeightLineProvider {
        boolean isHeightLine(int position);
    }

    public TimeLineView(Context context) {
        this(context, null);

    }

    public TimeLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prePare();
    }

    /**
     * 初始化
     */
    private void prePare() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineStoke);
        linePaint.setStyle(Paint.Style.FILL);


        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(pointColor);
        pointPaint.setDither(true);
        pointPaint.setStyle(Paint.Style.FILL);
        setOrientation(VERTICAL);
    }


    /**
     * 设置供用户选择是否高亮的接口
     *
     * @param provider
     */
    public void setHeightLineProvider(HeightLineProvider provider) {
        this.mHeightLineProvider = provider;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTimeLine(canvas);
    }

    /**
     * 绘画时间线
     *
     * @param canvas
     */
    private void drawTimeLine(Canvas canvas) {
        //没有子View，不用绘画
        if (getChildCount() == 0)
            return;

        //计算起点和终点位置，并且画出所有点
        countFirstPoint();
        countLastPoint();
        drawJoinLine(canvas);
        drawPoint(canvas);
    }

    /**
     * 计算起点位置
     */
    private void countFirstPoint() {
        firstX = leftAndRightMargin;
        View view = getChildAt(0);
        firstY = (int) (view.getY() + view.getHeight() / 2);
    }

    /**
     * 计算终点位置
     */
    private void countLastPoint() {
        lastX = leftAndRightMargin;
        View lastView = getChildAt(getChildCount() - 1);
        lastY = (int) (lastView.getY() + lastView.getHeight() / 2);
    }

    /**
     * 画线
     * @param canvas
     */
    private void drawJoinLine(Canvas canvas) {
        countLastPoint();
        canvas.drawLine(firstX, firstY, lastX, lastY, linePaint);
    }

    /**
     * 画出点
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {

        for (int i = 0; i < getChildCount(); i++) {
            int x = leftAndRightMargin;
            View view = getChildAt(i);
            int y = (int) (view.getY() + view.getHeight() / 2);

            /**
             * 如果用户设置了选择状态的权利并且返回真并且设置了高亮图片，则画出高亮状态，否则画出正常状态
             */
            if (mHeightLineProvider != null && mHeightLineProvider.isHeightLine(i) && mIcon != null) {
                drawHeightLine(canvas, x, y);
            } else {
                drawNorPoint(canvas, x, y);
            }

        }

    }


    /**
     * 画正常点
     * @param canvas
     * @param x
     * @param y
     */
    private void drawNorPoint(Canvas canvas, int x, int y) {
        canvas.drawCircle(x, y, pointRadius, pointPaint);
    }

    /**
     * 画出高亮点
     * @param canvas
     * @param x
     * @param y
     */
    private void drawHeightLine(Canvas canvas, int x, int y) {
        canvas.drawBitmap(mIcon, x - mIcon.getWidth() / 2, y - mIcon.getHeight() / 2, null);
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    /**
     * 设置高亮点的图片
     * @param bitmap
     */
    public void setHeightBitmap(Bitmap bitmap) {
        this.mIcon = bitmap;
    }
}
