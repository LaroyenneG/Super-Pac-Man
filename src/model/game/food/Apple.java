package model.game.food;

public final class Apple extends Food {
    public static final int POINTS = 10000;
    public static final double PROBABILITY = 1.0 / 50.0;

    public Apple() {
        super(POINTS, PROBABILITY);
    }
}
