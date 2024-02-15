package model.game.grid.square.door;

import model.game.entity.individual.Individual;
import model.game.grid.square.Passable;

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
