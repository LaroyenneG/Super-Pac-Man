package model.entity.individual.pac.person;

import model.entity.food.Food;
import model.entity.individual.Individual;
import model.entity.individual.ghost.Ghost;

import java.awt.*;


public abstract class PacPerson extends Individual {

    public static final double MAX_WEIGHT = 100.0;
    public static final double MIN_WEIGHT = 1.0;

    public static final double MIN_SPEED = 0.5;
    public static final double MAX_SPEED = 10.0;

    protected double weight;

    public PacPerson(Color color) {
        super(color);
        this.weight = MIN_WEIGHT;
    }

    public void grow(double weight) {
        this.weight = Math.min(MAX_WEIGHT, Math.max(MIN_WEIGHT, this.weight + weight));
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public final boolean isHero() {
        return true;
    }

    @Override
    public boolean eats(Food food) {

        var points = food.getPoints();

        var weight = points / 1000.0;

        grow(weight);

        return true;
    }

    @Override
    public boolean eats(PacPerson pacPerson) {
        return false;
    }

    @Override
    public boolean eats(Ghost ghost) {
        return ghost.isScared();
    }

    public final boolean pushes(PacPerson pacPerson) {
        return pacPerson.weight < weight;
    }

    public final boolean isGiant() {
        return weight >= MAX_WEIGHT;
    }

    @Override
    public double speed() {
        return 0;
    }
}
