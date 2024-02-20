package model.entity.food.ability;

import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacDevil;
import model.entity.individual.pac.person.PacPerson;
import model.entity.individual.pac.person.SuperPac;
import model.grid.Grid;
import model.grid.GridControl;

import java.util.Set;

public final class Star extends Food implements Ability {

    public static final int POINTS = 0;
    public static final double PROBABILITY = 1.0 / 20.0;
    public static final boolean UNIQUE = true;


    public Star() {
        super(POINTS, PROBABILITY, UNIQUE);
    }

    @Override
    public void apply(PacPerson owner, GridControl grid) {
        grid.evolve(new SuperPac(owner));
    }
}
