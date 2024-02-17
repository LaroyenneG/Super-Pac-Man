package model.entity.food.ability;

import model.entity.food.PacGum;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

import java.util.Set;

public final class SuperPacGum extends PacGum implements Ability {

    public static final double PROBABILITY = 1.0 / 10.0;

    public SuperPacGum() {
        super(PROBABILITY);
    }

    @Override
    public void apply(PacPerson owner, Set<Ghost> ghosts, Set<PacPerson> others) {
        for (var ghost : ghosts) {
            ghost.scareOff();
        }
    }
}
