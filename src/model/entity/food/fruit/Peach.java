package model.entity.food.fruit;

public final class Peach extends Fruit {

    public static final int POINTS = 5000;
    public static final double PROBABILITY = 1.0 / 40.0;

    public Peach() {
        super(POINTS, PROBABILITY);
    }
}
