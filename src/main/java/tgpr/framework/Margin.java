package tgpr.framework;

public class Margin {
    private final int left;
    private final int right;
    private final int top;
    private final int bottom;

    public Margin(int all) {
        this(all, all);
    }

    public Margin(int horizontal, int vertical) {
        this(horizontal, vertical, horizontal, vertical);
    }

    public Margin(int left, int top, int right, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public static Margin getDefault() {
        return Margin.of(1, 0);
    }

    public static Margin of(int all) {
        return new Margin(all);
    }

    public static Margin of(int horizontal, int vertical) {
        return new Margin(horizontal, vertical);
    }

    public static Margin of(int left, int top, int right, int bottom) {
        return new Margin(left, top, right, bottom);
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }
}
