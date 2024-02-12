package model.game.grid.square.door;

import model.game.character.Character;
import model.game.grid.square.Passable;
import model.game.grid.square.Square;

public abstract class Door extends Passable {

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
