package model.game.entity.food.fruit;

public final class Apple extends Fruit {
    public static final int POINTS = 10000;
    public static final double PROBABILITY = 1.0 / 50.0;

    public Apple() {
        super(POINTS, PROBABILITY);
    }
}
