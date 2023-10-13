package tgpr.framework;

public class Spacing {
    private final int horizontal;
    private final int vertical;

    public Spacing(int all) {
        this(all, all);
    }

    public Spacing(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public static Spacing getDefault() {
        return Spacing.of(1, 0);
    }

    public static Spacing of(int all) {
        return new Spacing(all);
    }

    public static Spacing of(int horizontal, int vertical) {
        return new Spacing(horizontal, vertical);
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }
}
