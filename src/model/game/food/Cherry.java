package model.game.food;

public final class Cherry extends Food {
    public static final int POINTS = 1000;
    public static final double PROBABILITY = 1.0 / 3.0;

    public Cherry() {
        super(POINTS, PROBABILITY);
    }
}
