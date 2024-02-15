package model.game.grid.square.door;

import model.game.entity.character.Character;
import model.game.entity.character.pac.person.PacPerson;

public final class PacDoor extends Door {
    private boolean open;

    @Override
    public boolean authorize(Character character) {

        if (!character.isHero()) return false;

        var pacPerson = (PacPerson) character;

        return !pacPerson.isGiant();
    }
}
