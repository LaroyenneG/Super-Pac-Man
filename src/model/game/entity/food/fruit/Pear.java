package model.game.entity.food.fruit;

public final class Pear extends Fruit {

    public static final int POINTS = 7000;
    public static final double PROBABILITY = 1.0 / 60.0;

    public Pear() {
        super(POINTS, PROBABILITY);
    }
}
