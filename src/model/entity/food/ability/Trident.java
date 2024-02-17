package model.entity.food.ability;

import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

import java.util.Set;

public final class Trident extends Food implements Ability {


    public static final int POINTS = 0;
    public static final double PROBABILITY = 1.0 / 666.0;
    public static final boolean UNIQUE = true;

    public Trident() {
        super(POINTS, PROBABILITY, UNIQUE);
    }

    @Override
    public void apply(PacPerson owner, Set<Ghost> ghosts, Set<PacPerson> others) {

    }
}
