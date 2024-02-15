package model.game.entity.food.ability;

import model.game.entity.individual.ghost.Ghost;
import model.game.entity.individual.pac.person.PacPerson;
import model.game.entity.food.PacGum;

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
