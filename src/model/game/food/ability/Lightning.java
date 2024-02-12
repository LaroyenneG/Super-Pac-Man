package model.game.food.ability;

import model.game.character.ghost.Ghost;
import model.game.character.pac.person.PacPerson;
import model.game.food.Food;

import java.util.Set;

public final class Lightning extends Food implements Ability {

    public static final int POINTS = 0;
    public static final double PROBABILITY = 1.0 / 15.0;
    public static final boolean UNIQUE = false;

    public Lightning() {
        super(POINTS, PROBABILITY, UNIQUE);
    }

    @Override
    public void apply(PacPerson owner, Set<Ghost> ghosts, Set<PacPerson> others) {

    }
}
