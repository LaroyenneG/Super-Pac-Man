package model.game.entity.individual;


import model.game.Heading;
import model.game.entity.Entity;
import model.game.entity.food.Food;
import stdlib.StdRandom;

public abstract class Individual extends Entity {

    private Heading heading = Heading.values()[StdRandom.uniformInt(Heading.values().length)];
    private final boolean moving = StdRandom.bernoulli();
    private double speed;


    protected Individual() {

    }


    public abstract boolean isHero();

    public abstract boolean eats(Individual individual);

    public abstract boolean eats(Food food);

    public Heading getHeading() {
        return heading;
    }

    public boolean isMoving() {
        return moving;
    }
}
