package model.entity.food;

public class PacGum extends Food {

    public static final int POINTS = 10;

    public static final double PROBABILITY = 0.8;
    public static final boolean UNIQUE = false;

    public PacGum() {
        this(PROBABILITY);
    }

    public PacGum(double probability) {
        super(POINTS, probability, UNIQUE);
    }
}
