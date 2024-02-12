package model.game.grid.square;

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
}
