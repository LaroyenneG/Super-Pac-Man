package model.grid.square;

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
}
