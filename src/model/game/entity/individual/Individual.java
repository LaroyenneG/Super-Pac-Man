package model.game.entity.individual;


import model.game.Heading;
import model.game.entity.Entity;
import model.game.entity.food.Food;

public abstract class Individual extends Entity {

    private final boolean moving;
    private final Heading heading;
    private double speed;


    protected Individual(double speed) {
        heading = null;
        moving = false;
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
