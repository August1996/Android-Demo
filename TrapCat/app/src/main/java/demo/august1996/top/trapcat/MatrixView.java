package demo.august1996.top.trapcat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Vector;

/**
 * Created by August on 16/8/9.
 */
public class MatrixView extends SurfaceView {

    private static final int ROW = 10;
    private static final int COL = 10;
    private static final int COUNT_ON = 10;
    private static final int DIRECTION_LEFT = 200;
    private static final int DIRECTION_LEFTUP = 201;
    private static final int DIRECTION_RIGHTUP = 202;
    private static final int DIRECTION_RIGHT = 203;
    private static final int DIRECTION_RIGHTDOWN = 204;
    private static final int DIRECTION_LEFTDOWN = 205;
    private Dot[][] mMatrix = new Dot[ROW][COL];
    private int eachWidth;
    private Cat cat;


    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            eachWidth = (surfaceHolder.getSurfaceFrame().width() * 2 / (2 * COL + 1));
            initGameMemory();
            initGameLocation();
            initGameStatus();
            redraw();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    public MatrixView(Context context) {
        super(context);
        getHolder().addCallback(mCallback);
    }


    private void redraw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.LTGRAY);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                Dot dot = mMatrix[i][j];
                if (dot.getState() == Dot.STATUS_OFF) {
                    paint.setColor(0xFFEEEEEE);
                } else if (dot.getState() == Dot.STATUS_ON) {
                    paint.setColor(0xFFFFAAAA);
                } else if (dot.getState() == Dot.STATUS_IN) {
                    paint.setColor(0xFFFF0000);
                }
                RectF rectF;
                if (i % 2 == 0) {
                    rectF = new RectF(dot.getX(), dot.getY(), dot.getX() + eachWidth, dot.getY() + eachWidth);
                } else {
                    rectF = new RectF(dot.getX(), dot.getY(), dot.getX() + eachWidth, dot.getY() + eachWidth);
                }
                canvas.drawOval(rectF, paint);

            }
        }
        getHolder().unlockCanvasAndPost(canvas);
    }


    private void initGameMemory() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                mMatrix[i][j] = new Dot();
            }
        }
    }

    private void initGameStatus() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                mMatrix[i][j].setState(Dot.STATUS_OFF);
            }
        }
        for (int i = 0; i < COUNT_ON; ) {
            int j = (int) (Math.random() * ROW);
            int k = (int) (Math.random() * COL);
            if (mMatrix[j][k].getState() == Dot.STATUS_OFF) {
                mMatrix[j][k].setState(Dot.STATUS_ON);
                i++;
            }
        }

        while (true) {
            int j = (int) (Math.random() * ROW);
            int k = (int) (Math.random() * COL);
            if (mMatrix[j][k].getState() == Dot.STATUS_OFF && !isEdge(j, k)) {
                mMatrix[j][k].setState(Dot.STATUS_IN);
                cat = new Cat(mMatrix[j][k], j, k);
                break;
            }
        }
    }

    private void initGameLocation() {
        int offset = eachWidth / 2;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                int x = j * eachWidth;
                if (i % 2 != 0) {
                    x += offset;
                }
                int y = i * eachWidth;
                mMatrix[i][j].setXY(x, y);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int row = (int) (event.getY() / eachWidth);
            int col = (int) (event.getX() / eachWidth);
            if (col >= COL || row >= ROW || col < 0 || row < 0) {
                initGameStatus();
                redraw();
            } else {
                touchDot(mMatrix[row][col]);
            }
        }
        return true;
    }

    public void touchDot(Dot dot) {
        if (dot.getState() == Dot.STATUS_OFF) {
            dot.setState(Dot.STATUS_ON);
            moveCat();
            redraw();
        }
    }

    private boolean isEdge(int i, int j) {
        if (i == 0 || j == 0 || i == ROW - 1 || j == COL - 1) {
            return true;
        }
        return false;
    }

    private void moveCat() {
        Vector<Integer> distanceList = new Vector<>();
        distanceList.add(getDistance(cat, DIRECTION_LEFT));
        distanceList.add(getDistance(cat, DIRECTION_LEFTUP));
        distanceList.add(getDistance(cat, DIRECTION_RIGHTUP));
        distanceList.add(getDistance(cat, DIRECTION_RIGHT));
        distanceList.add(getDistance(cat, DIRECTION_RIGHTDOWN));
        distanceList.add(getDistance(cat, DIRECTION_LEFTDOWN));
        int minIndex = 0;
        for (int i = 1; i < distanceList.size(); i++) {
            if (isBetterThan(distanceList.get(i), distanceList.get(minIndex))) {
                minIndex = i;
            }
        }
        int i = cat.getI();
        int j = cat.getJ();
        switch (minIndex) {
            case 0:
                j--;
                break;
            case 1:
                if (i % 2 == 1) {
                    i--;
                } else {
                    i--;
                    j--;
                }
                break;
            case 2:
                if (i % 2 == 1) {
                    i--;
                    j++;
                } else {
                    i--;
                }
                break;
            case 3:
                j++;
                break;
            case 4:
                if (i % 2 == 1) {
                    i++;
                    j++;
                } else {
                    i++;
                }
                break;
            case 5:
                if (i % 2 == 1) {
                    i++;
                } else {
                    i++;
                    j--;
                }
                break;
        }
        mMatrix[cat.getI()][cat.getJ()].setState(Dot.STATUS_OFF);
        mMatrix[i][j].setState(Dot.STATUS_IN);
        cat = new Cat(mMatrix[i][j], i, j);
        redraw();
        if (isLost()) {
            setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "你输了", Toast.LENGTH_SHORT).show();
                    initGameStatus();
                    redraw();
                    setEnabled(true);
                }
            }, 200);
        } else if (isWin()) {
            setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "你赢了", Toast.LENGTH_SHORT).show();
                    initGameStatus();
                    redraw();
                    setEnabled(true);
                }
            }, 200);
        }
    }


    private boolean isWin() {
        if (mMatrix[cat.getI() + 1][cat.getJ()].getState() == Dot.STATUS_ON
                && mMatrix[cat.getI() - 1][cat.getJ()].getState() == Dot.STATUS_ON
                && mMatrix[cat.getI() + 1][cat.getJ() + 1].getState() == Dot.STATUS_ON
                && mMatrix[cat.getI() + 1][cat.getJ() - 1].getState() == Dot.STATUS_ON
                && mMatrix[cat.getI() - 1][cat.getJ() + 1].getState() == Dot.STATUS_ON
                && mMatrix[cat.getI() - 1][cat.getJ() - 1].getState() == Dot.STATUS_ON) {
            return true;
        }
        return false;
    }


    public boolean isLost() {

        if (isEdge(cat.getI(), cat.getJ())) {
            return true;
        }

        return false;
    }

    private int getDistance(Cat cat, int dir) {
        int distance = 0;
        int i = cat.getI();
        int j = cat.getJ();
        while (true) {
            distance++;
            switch (dir) {
                case MatrixView.DIRECTION_LEFT:
                    j--;
                    break;
                case MatrixView.DIRECTION_LEFTUP:
                    if (i % 2 == 1) {
                        i--;
                    } else {
                        i--;
                        j--;
                    }
                    break;
                case MatrixView.DIRECTION_RIGHTUP:
                    if (i % 2 == 1) {
                        i--;
                        j++;
                    } else {
                        i--;
                    }
                    break;
                case MatrixView.DIRECTION_RIGHT:
                    j++;
                    break;
                case MatrixView.DIRECTION_RIGHTDOWN:
                    if (i % 2 == 1) {
                        i++;
                        j++;
                    } else {
                        i++;
                    }
                    break;
                case MatrixView.DIRECTION_LEFTDOWN:
                    if (i % 2 == 1) {
                        i++;
                    } else {
                        i++;
                        j--;
                    }
                    break;

            }
            if (mMatrix[i][j].getState() == Dot.STATUS_ON) {
                distance = distance * -1;
                break;
            }
            if (isEdge(i, j)) {
                break;
            }
        }
        return distance;
    }

    private boolean isBetterThan(int d1, int d2) {
        if ((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) {
            if (d1 > d2) {
                return true;
            } else {
                return false;
            }
        } else {
            if (d1 > d2) {
                return false;
            } else {
                return true;
            }
        }
    }
}

