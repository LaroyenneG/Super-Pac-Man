package model.game.grid.square.door;

import model.game.character.GameCharacter;

public final class HauntedDoor extends Door {

    @Override
    public boolean authorize(GameCharacter character) {
        return !character.isHero();
    }
}
