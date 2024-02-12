package model.game.food;

import model.game.grid.square.Square;

public abstract class Food extends Square {

    private final int score = 0;

    @Override
    public boolean isImpassable() {
        return false;
    }
}
