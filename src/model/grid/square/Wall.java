package model.grid.square;

import model.entity.Entity;

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
    public boolean accept(Entity entity) {
        return false;
    }
}
