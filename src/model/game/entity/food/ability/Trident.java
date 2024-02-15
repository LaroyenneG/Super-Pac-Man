package model.game.entity.food.ability;

import model.game.entity.individual.ghost.Ghost;
import model.game.entity.individual.pac.person.PacPerson;
import model.game.entity.food.Food;


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
