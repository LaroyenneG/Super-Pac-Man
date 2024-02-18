package model.entity.individual;


import model.Heading;
import model.entity.Entity;
import model.entity.food.Food;

import java.awt.*;

public abstract class Individual extends Entity {
    private final Color color;
    private boolean moving;
    private Heading heading;
    private boolean alive;
    private double weight;

    public Individual(Color color) {
        this.color = color;
        heading = null;
        moving = false;
        alive = true;
        weight = 0.0;
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

    public abstract boolean eats(Individual individual);

    public abstract boolean eats(Food food);

    public Color getColor() {
        return this.color;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public double getWeight() {
        return weight;
    }
}
