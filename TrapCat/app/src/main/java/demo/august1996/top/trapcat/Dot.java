package demo.august1996.top.trapcat;

/**
 * Created by August on 16/8/9.
 */
public class Dot {
    public static final int STATUS_ON = 100;
    public static final int STATUS_OFF = 101;
    public static final int STATUS_IN = 102;

    private int x;
    private int y;
    private int state = STATUS_OFF;

    public Dot() {
    }

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dot) {
            Dot dot = (Dot) obj;
            if (getX() == dot.getX() && getY() == dot.getY() && getState() == dot.getState()) {
                return true;
            }
        }
        return false;
    }
}
