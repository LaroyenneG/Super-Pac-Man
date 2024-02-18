package model.grid.square;

import model.entity.Entity;

public abstract class Square {

    public abstract boolean isImpassable();

    public abstract boolean isFree();

    public abstract boolean accept(Entity entity);
}
