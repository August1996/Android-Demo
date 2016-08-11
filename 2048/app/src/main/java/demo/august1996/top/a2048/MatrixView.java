package demo.august1996.top.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Vector;

/**
 * Created by August on 16/8/10.
 */
public class MatrixView extends SurfaceView {


    private static final int ROW = 4;
    private static final int COL = 4;
    private static final int MARGIN = 5;
    private Element[][] mMatrix = new Element[ROW][COL];

    private int eachWidth;
    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            eachWidth = surfaceHolder.getSurfaceFrame().width() / ROW;
            initMemory();
            initLocation();
            initGameStatus();
            redraw();
        }


        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    private void redraw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.LTGRAY);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                drawElement(canvas, mMatrix[i][j]);
            }
        }
        getHolder().unlockCanvasAndPost(canvas);
    }

    private void drawElement(Canvas canvas, Element element) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFFAAAA + element.getValue() * 10);
        Rect rect = new Rect(element.getX() + MARGIN, element.getY() + MARGIN, element.getX() + eachWidth - MARGIN, element.getY() + eachWidth - MARGIN);
        canvas.drawRect(rect, paint);
        if (element.getValue() != 0) {
            paint.setColor(Color.YELLOW);
            paint.setTextSize(100);
            String text = String.valueOf(element.getValue());
            Rect textRect = new Rect();
            paint.getTextBounds(text, 0, text.length(), textRect);
            canvas.drawText(text, 0, text.length(), element.getX() + (eachWidth - MARGIN - textRect.width()) / 2, element.getY() + (eachWidth - MARGIN + textRect.height()) / 2, paint);
        }
    }


    private void initMemory() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                mMatrix[i][j] = new Element();
            }
        }
    }

    private void initLocation() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                int x = (j % COL) * eachWidth;
                int y = i * eachWidth;
                mMatrix[i][j].setXY(x, y);
            }
        }
    }

    private void initGameStatus() {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                mMatrix[i][j].setValue(0);
            }
        }
        generateNewNumber(2);

    }


    private void generateNewNumber(int count) {
        for (int k = 0; k < count; ) {
            int i = (int) (Math.random() * ROW);
            int j = (int) (Math.random() * COL);
            if (mMatrix[i][j].getValue() == 0) {
                mMatrix[i][j].setValue(2);
                k++;
            }
        }
    }

    public MatrixView(Context context) {
        super(context);
        getHolder().addCallback(mCallback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            transformMatrix(keyCode);
            mergerMatrix(keyCode);
            //TODO 判断输赢
        }
        return true;
    }

    private void mergerMatrix(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                for (int i = 0; i < ROW; i++) {
                    boolean flag;
                    do {
                        flag = false;
                        for (int j = 0; j < COL - 1; j++) {
                            if (mMatrix[i][j].getValue() == mMatrix[i][j + 1].getValue() && mMatrix[i][j].getValue() != 0) {
                                mMatrix[i][j].setValue(mMatrix[i][j].getValue() * 2);
                                mMatrix[i][j + 1].setValue(0);
                                flag = true;
                            }
                        }
                        transformMatrix(KeyEvent.KEYCODE_DPAD_LEFT);
                    } while (flag);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                for (int j = 0; j < COL; j++) {
                    boolean flag;
                    do {
                        flag = false;
                        for (int i = 0; i < ROW - 1; i++) {
                            if (mMatrix[i][j].getValue() == mMatrix[i + 1][j].getValue() && mMatrix[i][j].getValue() != 0) {
                                mMatrix[i][j].setValue(mMatrix[i][j].getValue() * 2);
                                mMatrix[i + 1][j].setValue(0);
                                flag = true;
                            }
                        }
                        transformMatrix(KeyEvent.KEYCODE_DPAD_UP);
                    } while (flag);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                for (int i = 0; i < ROW; i++) {
                    boolean flag;
                    do {
                        flag = false;
                        for (int j = COL - 1; j > 0; j--) {
                            if (mMatrix[i][j].getValue() == mMatrix[i][j - 1].getValue() && mMatrix[i][j].getValue() != 0) {
                                mMatrix[i][j].setValue(mMatrix[i][j].getValue() * 2);
                                mMatrix[i][j - 1].setValue(0);
                                flag = true;
                            }
                        }
                        transformMatrix(KeyEvent.KEYCODE_DPAD_RIGHT);
                    } while (flag);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                for (int j = 0; j < COL; j++) {
                    boolean flag;
                    do {
                        flag = false;
                        for (int i = ROW - 1; i > 0; i--) {
                            if (mMatrix[i][j].getValue() == mMatrix[i - 1][j].getValue() && mMatrix[i][j].getValue() != 0) {
                                mMatrix[i][j].setValue(mMatrix[i][j].getValue() * 2);
                                mMatrix[i - 1][j].setValue(0);
                                flag = true;
                            }
                        }
                        transformMatrix(KeyEvent.KEYCODE_DPAD_DOWN);
                    } while (flag);
                }
                break;
        }
        generateNewNumber(1);
        redraw();
    }


    private void transformMatrix(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                for (int i = 0; i < ROW; i++) {
                    Vector<Element> nonEmptyList = new Vector<>();
                    for (int j = 0; j < COL; j++) {
                        if (mMatrix[i][j].getValue() != 0) {
                            nonEmptyList.add(mMatrix[i][j]);
                        }
                    }
                    for (int j = 0; j < nonEmptyList.size(); j++) {
                        int value = nonEmptyList.get(j).getValue();
                        nonEmptyList.get(j).setValue(0);
                        mMatrix[i][j].setValue(value);
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                for (int j = 0; j < COL; j++) {
                    Vector<Element> nonEmptyList = new Vector<>();
                    for (int i = 0; i < ROW; i++) {
                        if (mMatrix[i][j].getValue() != 0) {
                            nonEmptyList.add(mMatrix[i][j]);
                        }
                    }
                    for (int i = 0; i < nonEmptyList.size(); i++) {
                        int value = nonEmptyList.get(i).getValue();
                        nonEmptyList.get(i).setValue(0);
                        mMatrix[i][j].setValue(value);
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                for (int i = 0; i < ROW; i++) {
                    Vector<Element> nonEmptyList = new Vector<>();
                    for (int j = 0; j < COL; j++) {
                        if (mMatrix[i][j].getValue() != 0) {
                            nonEmptyList.add(mMatrix[i][j]);
                        }
                    }
                    for (int j = COL - 1, k = nonEmptyList.size() - 1; k >= 0; k--, j--) {
                        int value = nonEmptyList.get(k).getValue();
                        nonEmptyList.get(k).setValue(0);
                        mMatrix[i][j].setValue(value);
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                for (int j = 0; j < COL; j++) {
                    Vector<Element> nonEmptyList = new Vector<>();
                    for (int i = 0; i < ROW; i++) {
                        if (mMatrix[i][j].getValue() != 0) {
                            nonEmptyList.add(mMatrix[i][j]);
                        }
                    }
                    for (int i = ROW - 1, k = nonEmptyList.size() - 1; k >= 0; k--, i--) {
                        int value = nonEmptyList.get(k).getValue();
                        nonEmptyList.get(k).setValue(0);
                        mMatrix[i][j].setValue(value);
                    }
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                initGameStatus();
                redraw();
                break;
        }
    }

    private boolean isWin() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (mMatrix[i][j].getValue() >= 2048) {
                    return true;
                }
            }
        }
        return false;
    }

}
