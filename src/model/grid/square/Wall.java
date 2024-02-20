package model.grid.square;

import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

public final class Wall extends Square {
    @Override
    public String toString() {
        return "+";
    }

    @Override
    public boolean isImpassable() {
        return true;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public boolean accept(PacPerson pacPerson) {
        return pacPerson.canFly();
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
