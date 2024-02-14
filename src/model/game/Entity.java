package model.game;

import stdlib.StdRandom;

import java.awt.*;

public abstract class Entity {

    private final Point position;

    public Entity() {
        position = new Point(StdRandom.uniformInt(20), StdRandom.uniformInt(20));
    }

    public Point getPosition() {
        return position;
    }
}
