package model.game.entity.character;


import model.game.Heading;
import model.game.entity.Entity;
import model.game.entity.food.Food;
import stdlib.StdRandom;

public abstract class Character extends Entity {

    private Heading heading = Heading.values()[StdRandom.uniformInt(Heading.values().length)];
    private final boolean moving = StdRandom.bernoulli();
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
