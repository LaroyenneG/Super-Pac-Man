package model.game.food;

public final class Banana extends Food {
    public static final int POINTS = 50000;
    public static final double PROBABILITY = 1.0 / 10.0;

    public Banana() {
        super(POINTS, PROBABILITY);
    }
}
