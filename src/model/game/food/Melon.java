package model.game.food;

public final class Melon extends Food {
    public static final int POINTS = 30000;
    public static final double PROBABILITY = 1.0 / 80.0;

    public Melon() {
        super(POINTS, PROBABILITY);
    }
}
