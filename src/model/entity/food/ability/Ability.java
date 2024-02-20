package model.entity.food.ability;

import model.entity.individual.pac.person.PacPerson;
import model.grid.GridAbilityInterface;

public interface Ability {

    double DEFAULT_DURATION = 5.0;

    void apply(PacPerson owner, GridAbilityInterface grid);


    default double duration() {
        return DEFAULT_DURATION;
    }
}
