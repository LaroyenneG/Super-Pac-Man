package model.game.character.ghost;

import model.game.character.Character;
import model.game.character.pac.person.PacPerson;
import model.game.food.Food;

import java.awt.*;

public abstract class Ghost extends Character {

    private final Color color;
    private boolean scared;


    public Ghost(Color color) {
        this.color = color;
        scared = false;
    }


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
    public final boolean eats(Character character) {

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

    @Override
    public String toString() {
        return "^";
    }

    public Color getColor() {
        return this.color;
    }
}
