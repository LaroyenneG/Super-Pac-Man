package model.grid.square;

import model.entity.Entity;
import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

public final class Space extends Passable {

    @Override
    public String toString() {
        return " ";
    }


    @Override
    public boolean isImpassable() {
        return false;
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public boolean accept(PacPerson pacPerson) {
        return true;
    }

    @Override
    public boolean accept(Ghost ghost) {
        return true;
    }

    @Override
    public boolean accept(Food food) {
        return true;
    }
}
