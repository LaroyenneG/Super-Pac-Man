package model.game.character.ghost;

import model.game.character.GameCharacter;
import model.game.character.pac.person.PacPerson;

public abstract class Ghost extends GameCharacter {

    private boolean frozen;

    private boolean scared;


    @Override
    public final boolean isHero() {
        return true;
    }

    public boolean isScared() {
        return scared;
    }

    @Override
    public boolean eats(GameCharacter character) {

        if(!character.isHero()) return false;

        var pacPerson = (PacPerson) character;
        /*
        if(scared && pacPerson.) {

        }
         */

        return false;
    }
}
