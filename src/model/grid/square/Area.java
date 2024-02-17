package model.grid.square;

public final class Area extends Passable {

    @Override
    public boolean isImpassable() {
        return false;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
