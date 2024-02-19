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

    public void move(int x, int y) {
        assert x == 0 || y == 0;
        this.position = new Point(this.position.x + x, this.position.y + y);
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

    public void move() {
        switch (heading) {
            case UP -> {
                move(0, -1);
            }
            case DOWN -> {
                move(0, 1);
            }
            case RIGHT -> {
                move(1, 0);
            }
            case LEFT -> {
                move(-1, 0);
            }
            case null, default -> {
            }
        }
    }
}
