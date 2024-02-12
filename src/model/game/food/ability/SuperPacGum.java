package model.game.food.ability;

import model.game.character.ghost.Ghost;
import model.game.character.pac.person.PacPerson;
import model.game.food.PacGum;

import java.util.Set;

public final class SuperPacGum extends PacGum implements Ability {

    public SuperPacGum() {
        super();
    }

    @Override
    public void apply(PacPerson owner, Set<Ghost> ghosts, Set<PacPerson> others) {

        for (var ghost : ghosts) {
            ghost.scareOff();
        }
    }
}
