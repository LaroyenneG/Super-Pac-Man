package model.game.entity.individual.pac.person;

import java.awt.*;

public final class BabyPacMan extends PacPerson {
    public static final double SPEED = 1.0;
    public static final double WEIGHT = 0.1;

    public BabyPacMan(Color color) {
        super(color, SPEED, WEIGHT);
    }
}
