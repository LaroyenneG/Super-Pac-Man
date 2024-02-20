package model.entity.food.ability;

import model.entity.food.Food;
import model.entity.individual.pac.person.PacDevil;
import model.entity.individual.pac.person.PacPerson;
import model.grid.GridAbilityInterface;

public final class Trident extends Food implements Ability {

    public static final int POINTS = 0;
    public static final double PROBABILITY = 1.0 / 666.0;
    public static final boolean UNIQUE = true;

    public Trident() {
        super(POINTS, PROBABILITY, UNIQUE);
    }

    @Override
    public void apply(PacPerson owner, GridAbilityInterface grid) {
        grid.evolve(new PacDevil(owner));
    }
}
