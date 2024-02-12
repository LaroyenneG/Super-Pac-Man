package model.game.food;

public class PacGum extends Food {
    public static final int POINTS = 10;
    public static final double PROBABILITY = 1.0;

    public PacGum() {
        this(PROBABILITY);
    }

    public PacGum(double probability) {
        super(POINTS, probability);
    }
}
