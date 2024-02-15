package model.game.entity.individual.pac.person;

import model.game.entity.food.Food;
import model.game.entity.individual.Individual;


public abstract class PacPerson extends Individual {

    public static final int MAX_WEIGHT = 10;

    private final double weight;

    public PacPerson(double speed, double weight) {
        super(speed);
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
