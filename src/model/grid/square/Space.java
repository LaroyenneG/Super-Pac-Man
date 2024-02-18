package model.grid.square;

import model.entity.Entity;

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
    public boolean accept(Entity entity) {
        return true;
    }


    public boolean isVisited() {
        return true;
    }
}
