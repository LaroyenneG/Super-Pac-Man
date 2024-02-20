package model.entity.individual.pac.person;

import model.entity.food.Food;

import java.awt.*;

public final class SuperPac extends PacPerson {

    public static final double SUPER_WEIGHT = 0.0;

    public SuperPac(Color color) {
        super(color);
        weight = SUPER_WEIGHT;
    }

    public SuperPac(PacPerson pacPerson) {
        super(pacPerson);
        weight = SUPER_WEIGHT;
    }

    @Override
    public boolean eats(Food food) {
        return true;
    }
}
