package model.game.food;

public final class Pear extends Food {

    public static final int POINTS = 7000;
    public static final double PROBABILITY = 1.0 / 6.0;

    public Pear() {
        super(POINTS, PROBABILITY);
    }
}
