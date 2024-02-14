package model.game.character;

import model.game.Entity;
import model.game.food.Food;
import stdlib.StdRandom;

public abstract class Character extends Entity {

    static int count = 0;

    private Heading heading = Heading.values()[StdRandom.uniformInt(Heading.values().length)];
    private boolean moving = count++ % 2 == 0;
    private double speed;


    protected Character() {

    }


    public abstract boolean isHero();

    public abstract boolean eats(Character character);

    public abstract boolean eats(Food food);

    public Heading getHeading() {
        return heading;
    }

    public boolean isMoving() {
        return moving;
    }
}
