package model.entity.food.ability;

import model.entity.food.Food;
import model.entity.food.PacGum;
import model.entity.individual.pac.person.PacPerson;
import model.grid.Grid;
import model.grid.GridControl;

public final class SuperPacGum extends PacGum implements Ability {

    public static final double PROBABILITY = 1.0 / 10.0;

    public SuperPacGum() {
        super(PROBABILITY);
    }

    @Override
    public void apply(PacPerson owner, GridControl grid) {
        var ghosts = grid.getGhosts();
        for (var ghost : ghosts) {
            ghost.scareOff();
        }
    }
}
