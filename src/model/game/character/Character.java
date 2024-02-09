package model.game.character;

import model.game.grid.Square;

public abstract class Character extends Square {

    private final boolean moving;

    private final Heading heading;


    public Character() {
        moving = false;
        heading = null;
    }
}
