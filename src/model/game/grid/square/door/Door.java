package model.game.grid.square.door;

import model.game.character.GameCharacter;
import model.game.grid.square.Square;

public abstract class Door extends Square {

    public abstract boolean authorize(GameCharacter character);

    @Override
    public String toString() {
        return "~";
    }

    @Override
    public boolean isImpassable() {
        return false;
    }
}
