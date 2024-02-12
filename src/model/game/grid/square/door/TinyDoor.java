package model.game.grid.square.door;

import model.game.character.GameCharacter;

public final class TinyDoor extends Door {

    @Override
    public boolean authorize(GameCharacter character) {
        return false;
    }
}
