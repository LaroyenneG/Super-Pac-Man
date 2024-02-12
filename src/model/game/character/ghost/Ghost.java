package model.game.character.ghost;

import model.game.character.GameCharacter;
import model.game.character.pac.person.PacPerson;
import model.game.food.Food;

public abstract class Ghost extends GameCharacter {

    private boolean scared;

    public void scareOff() {
        scared = true;
    }

    public void reassure() {
        scared = false;
    }

    @Override
    public final boolean isHero() {
        return true;
    }

    public boolean isScared() {
        return scared;
    }

    @Override
    public final boolean eats(GameCharacter character) {

        if(!character.isHero()) return false;

        var pacPerson = (PacPerson) character;
        /*
        if(scared && pacPerson.) {

        }
         */

        return false;
    }

    @Override
    public final boolean eats(Food food) {
        return false;
    }
}
