package model.game.character.pac.person;

import model.game.character.Character;
import model.game.food.Food;

import java.awt.*;


public abstract class PacPerson extends Character {

    public static final Color DEFAULT_COLOR = Color.YELLOW;

    public static final int MAX_WEIGHT = 10;

    private final int weight = 0;

    public PacPerson(Color color) {
        super(color);
    }


    public int getWeight() {
        return weight;
    }

    @Override
    public final boolean isHero() {
        return true;
    }

    @Override
    public boolean eats(Character character) {
        return false;
    }

    @Override
    public final boolean eats(Food food) {
        return true;
    }


    public final boolean pushes(PacPerson pacPerson) {
        return pacPerson.weight < weight;
    }

    public final boolean isGiant() {
        return weight >= MAX_WEIGHT;
    }

    @Override
    public String toString() {
        return "C";
    }
}
