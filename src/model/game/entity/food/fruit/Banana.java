package model.game.entity.food.fruit;

public final class Banana extends Fruit {
    public static final int POINTS = 50000;
    public static final double PROBABILITY = 1.0 / 100.0;

    public Banana() {
        super(POINTS, PROBABILITY);
    }
}
