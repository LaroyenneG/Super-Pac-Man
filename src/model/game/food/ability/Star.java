package model.game.food.ability;

import model.game.character.ghost.Ghost;
import model.game.character.pac.person.PacPerson;
import model.game.food.Food;

import java.util.Set;

public final class Star extends Food implements Ability {

    public Star() {
        super();
    }

    @Override
    public void apply(PacPerson owner, Set<Ghost> ghosts, Set<PacPerson> others) {

    }
}
