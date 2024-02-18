package model.grid.square;

import model.entity.Entity;

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
    public boolean accept(Entity entity) {
        return false;
    }
}
