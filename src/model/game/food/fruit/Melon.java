package model.game.food.fruit;

public final class Melon extends Fruit {
    public static final int POINTS = 30000;
    public static final double PROBABILITY = 1.0 / 80.0;

    public Melon() {
        super(POINTS, PROBABILITY);
    }
}
