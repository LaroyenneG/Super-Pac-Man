package model.entity.food.ability;

import model.entity.food.PacGum;
import model.entity.individual.pac.person.PacPerson;
import model.grid.GridAbilityInterface;

public final class SuperPacGum extends PacGum implements Ability {

    public static final double PROBABILITY = 1.0 / 10.0;

    public SuperPacGum() {
        super(PROBABILITY);
    }

    @Override
    public void apply(PacPerson owner, GridAbilityInterface grid) {
        grid.scareOffGhosts();
    }
}
