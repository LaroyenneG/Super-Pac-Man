package model.entity.food.fruit;

public final class Cherry extends Fruit {
    public static final int POINTS = 1000;
    public static final double PROBABILITY = 1.0 / 30.0;

    public Cherry() {
        super(POINTS, PROBABILITY);
    }
}
