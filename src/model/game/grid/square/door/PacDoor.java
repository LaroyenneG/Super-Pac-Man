package model.game.grid.square.door;

import model.game.character.Character;
import model.game.character.pac.person.PacPerson;

public final class PacDoor extends Door {

    @Override
    public boolean authorize(Character character) {

        if (!character.isHero()) return false;

        var pacPerson = (PacPerson) character;

        return !pacPerson.isGiant();
    }
}
