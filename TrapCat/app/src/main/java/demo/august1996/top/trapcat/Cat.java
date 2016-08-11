package demo.august1996.top.trapcat;

/**
 * Created by August on 16/8/9.
 */
public class Cat extends Dot {
    private int i;
    private int j;

    public Cat(Dot dot, int i, int j) {
        this.i = i;
        this.j = j;
        setState(Dot.STATUS_IN);
        setX(dot.getX());
        setY(dot.getY());
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
