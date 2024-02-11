package model.game.grid.door;

import model.game.character.GameCharacter;
import model.game.grid.Square;

public abstract class Door extends Square {

    public abstract boolean authorize(GameCharacter character);
}
