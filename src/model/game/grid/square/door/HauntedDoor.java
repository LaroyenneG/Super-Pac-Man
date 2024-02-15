package model.game.grid.square.door;

import model.game.entity.character.Character;

public final class HauntedDoor extends Door {

    @Override
    public boolean authorize(Character character) {
        return !character.isHero();
    }
}
