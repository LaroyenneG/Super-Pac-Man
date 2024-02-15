package model.game.entity.food.ability;

import model.game.entity.individual.ghost.Ghost;
import model.game.entity.individual.pac.person.PacPerson;

import java.util.Set;

public interface Ability {

    double DEFAULT_DURATION = 5.0;

    void apply(PacPerson owner, Set<Ghost> ghosts, Set<PacPerson> others);


    default double duration() {
        return DEFAULT_DURATION;
    }
}
