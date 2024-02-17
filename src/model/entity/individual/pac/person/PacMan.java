package model.entity.individual.pac.person;

import java.awt.*;

public final class PacMan extends PacPerson {
    public static final double SPEED = 0.6;
    public static final double WEIGHT = 0.5;

    public PacMan(Color color) {
        super(color, SPEED, WEIGHT);
    }
}
