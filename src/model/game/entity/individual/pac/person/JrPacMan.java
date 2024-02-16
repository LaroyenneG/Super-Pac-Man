package model.game.entity.individual.pac.person;

import java.awt.*;

public final class JrPacMan extends PacPerson {
    public static final double SPEED = 0.8;
    public static final double WEIGHT = 0.3;

    public JrPacMan(Color color) {
        super(color, SPEED, WEIGHT);
    }
}
