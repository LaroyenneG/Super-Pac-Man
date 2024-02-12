package model.game.grid.square.door;

import model.game.character.Character;
import model.game.grid.square.Square;

public abstract class Door extends Square {

    public abstract boolean authorize(Character character);

    @Override
    public String toString() {
        return "~";
    }

    @Override
    public boolean isImpassable() {
        return false;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
