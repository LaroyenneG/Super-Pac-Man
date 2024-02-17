package model.game.entity;

import java.awt.*;

public abstract class Entity {

    protected Point position;

    public Entity() {
        position = new Point(0, 0);
    }


    public Point getPosition() {
        return (Point) position.clone();
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
