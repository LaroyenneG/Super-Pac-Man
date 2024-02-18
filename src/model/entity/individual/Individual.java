package model.entity.individual;


import model.Heading;
import model.entity.Entity;
import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

import java.awt.*;

public abstract class Individual extends Entity {
    private final Color color;
    private boolean moving;
    private Heading heading;
    private boolean alive;

    public Individual(Color color) {
        this.color = color;
        heading = null;
        moving = false;
        alive = true;
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

    public Heading getHeading() {
        return heading;
    }

    public boolean isMoving() {
        return moving;
    }

    public abstract boolean isHero();

    public abstract boolean eats(Food food);

    public abstract boolean eats(Ghost ghost);

    public abstract boolean eats(PacPerson pacPerson);

    public Color getColor() {
        return this.color;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public abstract double speed();
}
