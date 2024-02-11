package model.game.grid.door;

import model.game.character.GameCharacter;
import model.game.character.pac.person.PacPerson;

public final class PacDoor extends Door {

    @Override
    public boolean authorize(GameCharacter character) {

        if (!character.isHero()) return false;

        var pacPerson = (PacPerson) character;

        return !pacPerson.isGiant();
    }
}
