package model.game;

import java.awt.*;

public abstract class Entity {
    private final Point position;

    public Entity() {
        position = new Point(3,3);
    }

    public Point getPosition() {
        return position;
    }
}
