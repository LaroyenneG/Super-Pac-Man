package model.game.entity;

import stdlib.StdRandom;

import java.awt.*;

public abstract class Entity {

    private final Point position;

    public Entity() {
        position = new Point(StdRandom.uniformInt(15), StdRandom.uniformInt(15));
    }

    public Point getPosition() {
        return position;
    }
}
