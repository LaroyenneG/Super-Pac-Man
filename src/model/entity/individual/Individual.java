package model.entity.individual;


import model.Heading;
import model.entity.Entity;
import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

import java.awt.*;
import java.util.Objects;

public abstract class Individual extends Entity {

    protected final Color color;
    protected boolean moving;
    protected Heading heading;
    protected boolean alive;

    public Individual(Color color) {
        super();
        this.color = color;
        this.heading = null;
        this.moving = false;
        this.alive = true;
    }

    public Individual(Individual individual) {
        super(individual);
        this.color = individual.color;
        this.heading = individual.heading;
        this.moving = individual.moving;
        this.alive = individual.alive;
    }

    public void move(Point position) {
        this.position = position;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public boolean eats(Entity entity) {

        var position = entity.getPosition();

        return Objects.equals(this.position, position);
    }

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

    public Point targetPosition() {
        return switch (heading) {
            case UP -> new Point(position.x, position.y - 1);
            case DOWN -> new Point(position.x, position.y + 1);
            case RIGHT -> new Point(position.x + 1, position.y);
            case LEFT -> new Point(position.x - 1, position.y);
            case null, default -> position;
        };
    }


    public abstract double speed();

    public void move() {
        var position = targetPosition();
        move(position);
    }

    @Override
    public String toString() {
        return String.valueOf(color.hashCode());
    }
}
