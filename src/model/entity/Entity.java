package model.entity;

import java.awt.*;

public abstract class Entity {

    public static final Point DEFAULT_POSITION = new Point(0, 0);

    protected Point position;

    public Entity() {
        this(DEFAULT_POSITION);
    }

    public Entity(Point position) {
        this.position = position;
    }

    public Entity(Entity entity) {
        this(entity.position);
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
