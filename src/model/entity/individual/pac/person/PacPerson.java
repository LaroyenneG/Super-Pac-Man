package model.entity.individual.pac.person;

import model.entity.food.Food;
import model.entity.individual.Individual;

import java.awt.*;


public abstract class PacPerson extends Individual {

    public static final int MAX_WEIGHT = 1;

    private final double weight;

    public PacPerson(Color color, double speed, double weight) {
        super(color);
        this.weight = weight;
    }


    public double getWeight() {
        return weight;
    }

    @Override
    public final boolean isHero() {
        return true;
    }

    @Override
    public boolean eats(Individual individual) {
        return false;
    }

    @Override
    public final boolean eats(Food food) {
        return true;
    }


    public final boolean pushes(PacPerson pacPerson) {
        return pacPerson.weight < weight;
    }

    public final boolean isGiant() {
        return weight >= MAX_WEIGHT;
    }

    @Override
    public String toString() {
        return "C";
    }
}
