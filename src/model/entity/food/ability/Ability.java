package model.entity.food.ability;

import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;
import model.grid.Grid;
import model.grid.GridControl;

import java.util.Set;

public interface Ability {

    double DEFAULT_DURATION = 5.0;

    void apply(PacPerson owner, GridControl grid);


    default double duration() {
        return DEFAULT_DURATION;
    }
}
