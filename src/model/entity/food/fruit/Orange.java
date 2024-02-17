package model.entity.food.fruit;

public final class Orange extends Fruit {
    public static final int POINTS = 20000;
    public static final double PROBABILITY = 1.0 / 70.0;

    public Orange() {
        super(POINTS, PROBABILITY);
    }
}
