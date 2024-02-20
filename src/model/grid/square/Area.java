package model.grid.square;

import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

public final class Area extends Passable {

    @Override
    public boolean isImpassable() {
        return false;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public boolean accept(PacPerson pacPerson) {
        return !pacPerson.canFly();
    }

    @Override
    public boolean accept(Ghost ghost) {
        return true;
    }

    @Override
    public boolean accept(Food food) {
        return false;
    }
}
