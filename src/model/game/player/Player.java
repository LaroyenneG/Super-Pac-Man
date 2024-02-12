package model.game.player;

import model.game.character.pac.person.PacPerson;

public final class Player {

    private int lifePoints = 0;
    private int score = 0;

    private PacPerson avatar;

    private final String name;

    public Player(String name) {
        this.name = name;
    }
}
