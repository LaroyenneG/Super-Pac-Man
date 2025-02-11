package model.grid.square.door;

import model.entity.food.Food;
import model.entity.individual.Individual;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

public final class HauntedDoor extends Door {

    @Override
    public boolean authorize(Individual individual) {
        return !individual.isHero();
    }


    @Override
    public boolean accept(PacPerson pacPerson) {
        return false;
    }

    @Override
    public boolean accept(Ghost ghost) {
        return !ghost.isScared();
    }

    @Override
    public boolean accept(Food food) {
        return false;
    }
}
