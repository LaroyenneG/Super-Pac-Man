package model.game.food;

public final class Strawberry extends Food {
    public static final int POINTS = 3000;
    public static final double PROBABILITY = 1.0 / 35.0;

    public Strawberry() {
        super(POINTS, PROBABILITY);
    }
}
