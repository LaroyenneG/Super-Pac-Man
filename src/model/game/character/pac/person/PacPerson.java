package model.game.character.pac.person;

import model.game.character.GameCharacter;


public abstract class PacPerson extends GameCharacter {
    public static final int MAX_WEIGHT = 10;

    private final int weight = 0;


    public int getWeight() {

        return weight;
    }

    @Override
    public final boolean isHero() {
        return true;
    }

    @Override
    public boolean eats(GameCharacter character) {
        return false;
    }

    public final boolean pushes(PacPerson pacPerson) {
        return pacPerson.weight < weight;
    }

    public final boolean isGiant() {
        return weight >= MAX_WEIGHT;
    }


}
