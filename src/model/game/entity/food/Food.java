package model.game.entity.food;

import model.game.entity.Entity;

public abstract class Food extends Entity {
    private final int points;
    private final double probability;
    private final boolean unique;

    public Food(int points, double probability, boolean unique) {
        this.points = points;
        this.probability = probability;
        this.unique = unique;
    }


    public int getPoints() {
        return points;
    }

    public double getProbability() {
        return probability;
    }

    public boolean isUnique() {
        return unique;
    }
}
