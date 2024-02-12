package model.game.food;

import model.game.Entity;

public abstract class Food extends Entity {

    private final int points;
    private final double probability;

    public Food(int points, double probability) {
        this.points = points;
        this.probability = probability;
    }


    public int getPoints() {
        return points;
    }

    public double getProbability() {
        return probability;
    }
}
