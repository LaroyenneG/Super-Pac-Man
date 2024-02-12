package model.game.food;

import model.game.Entity;

public abstract class Food extends Entity {
    private static final int DEFAULT_SCORE = 0;

    private final int score;

    public Food(int score) {
        this.score = score;
    }

    public Food() {
        this(DEFAULT_SCORE);
    }
}
