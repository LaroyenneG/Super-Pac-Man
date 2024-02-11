package model.game.character.pac.person;

import model.game.character.GameCharacter;


public abstract class PacPerson extends GameCharacter {

    private final int score = 0;

    private final int size = 0;


    public int getSize() {

        return size;
    }

    @Override
    public boolean isHero() {
        return true;
    }

    @Override
    public boolean eat(GameCharacter character) {
        return false;
    }
}
