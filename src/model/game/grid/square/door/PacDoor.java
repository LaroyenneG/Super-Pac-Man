package model.game.grid.square.door;

import model.game.entity.individual.Individual;
import model.game.entity.individual.pac.person.PacPerson;

public final class PacDoor extends Door {
    private boolean open;

    @Override
    public boolean authorize(Individual individual) {

        if (!individual.isHero()) return false;

        var pacPerson = (PacPerson) individual;

        return !pacPerson.isGiant();
    }
}
