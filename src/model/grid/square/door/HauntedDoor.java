package model.grid.square.door;

import model.entity.individual.Individual;

public final class HauntedDoor extends Door {

    @Override
    public boolean authorize(Individual individual) {
        return !individual.isHero();
    }
}
