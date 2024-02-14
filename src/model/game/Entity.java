package model.game;

import stdlib.StdRandom;

import java.awt.*;

public abstract class Entity {

    private final Point position;

    public Entity() {
        position = new Point(5, 5);
    }

    public Point getPosition() {
        return position;
    }
}
