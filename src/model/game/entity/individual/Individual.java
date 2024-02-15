package model.game.entity.individual;


import model.game.Heading;
import model.game.entity.Entity;
import model.game.entity.food.Food;

import java.awt.*;

public abstract class Individual extends Entity {
    private final double speed;
    private boolean moving;
    private Heading heading;

    public Individual(double speed) {
        this.speed = speed;
        heading = null;
        moving = false;
    }

    public void move(Point position) {
        this.position = position;
        this.moving = false;
    }

    public void movable() {
        this.moving = true;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public double getSpeed() {
        return speed;
    }

    public Heading getHeading() {
        return heading;
    }

    public boolean isMoving() {
        return moving;
    }

    public abstract boolean isHero();

    public abstract boolean eats(Individual individual);

    public abstract boolean eats(Food food);
}
