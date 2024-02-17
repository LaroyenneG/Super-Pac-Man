package model.entity.food.fruit;

public final class Strawberry extends Fruit {

    public static final int POINTS = 3000;
    public static final double PROBABILITY = 1.0 / 35.0;

    public Strawberry() {
        super(POINTS, PROBABILITY);
    }
}
