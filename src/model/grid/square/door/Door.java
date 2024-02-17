package model.grid.square.door;

import model.entity.individual.Individual;
import model.grid.square.Passable;

public abstract class Door extends Passable {

    public abstract boolean authorize(Individual individual);

    @Override
    public String toString() {
        return "~";
    }

    @Override
    public boolean isImpassable() {
        return false;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
