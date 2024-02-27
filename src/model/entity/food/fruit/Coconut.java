package model.entity.food.fruit;

public final class Coconut extends Fruit {

    public static final int POINTS = 50000;
    public static final double PROBABILITY = 1.0 / 100.0;

    public Coconut() {
        super(POINTS, PROBABILITY);
    }
}
