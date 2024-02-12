package model.game.food.fruit;

import model.game.food.Food;

public abstract class Fruit extends Food {

    public static final boolean UNIQUE = true;

    public Fruit(int points, double probability) {
        super(points, probability, UNIQUE);
    }
}
