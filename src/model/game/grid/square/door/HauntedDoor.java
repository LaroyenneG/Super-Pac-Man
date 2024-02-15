package model.game.grid.square.door;

import model.game.entity.individual.Individual;

public final class HauntedDoor extends Door {

    @Override
    public boolean authorize(Individual individual) {
        return !individual.isHero();
    }
}
