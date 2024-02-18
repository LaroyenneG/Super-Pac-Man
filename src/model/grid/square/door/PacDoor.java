package model.grid.square.door;

import model.entity.Entity;
import model.entity.food.Food;
import model.entity.individual.Individual;
import model.entity.individual.ghost.Ghost;
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
    public boolean accept(PacPerson pacPerson) {
        return !pacPerson.isGiant() && !pacPerson.isFlying();
    }

    @Override
    public boolean accept(Ghost ghost) {
        return false;
    }

    @Override
    public boolean accept(Food food) {
        return false;
    }
}
