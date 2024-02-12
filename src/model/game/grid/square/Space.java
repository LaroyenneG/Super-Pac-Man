package model.game.grid.square;

public final class Space extends Square {

    @Override
    public String toString() {
        return " ";
    }


    @Override
    public boolean isImpassable() {
        return false;
    }
}
