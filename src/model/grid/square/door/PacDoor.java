package model.grid.square.door;

import model.entity.Entity;
import model.entity.individual.Individual;
import model.entity.individual.pac.person.PacPerson;

public final class PacDoor extends Door {
    private boolean open;

    @Override
    public boolean authorize(Individual individual) {

        if (!individual.isHero()) return false;

        var pacPerson = (PacPerson) individual;

        return !pacPerson.isGiant();
    }

    @Override
    public boolean accept(Entity entity) {
        return false;
    }
}
