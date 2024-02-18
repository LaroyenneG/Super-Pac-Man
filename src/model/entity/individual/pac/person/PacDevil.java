package model.entity.individual.pac.person;

import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;

import java.awt.*;

public final class PacDevil extends PacPerson {

    public PacDevil(Color color) {
        super(color);
        weight = MAX_WEIGHT;
    }

    @Override
    public boolean eats(Food food) {
        return false;
    }

    @Override
    public boolean eats(Ghost ghost) {
        return true;
    }

    @Override
    public boolean eats(PacPerson pacPerson) {
        return weight > pacPerson.weight;
    }
}
