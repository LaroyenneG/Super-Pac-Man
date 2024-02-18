package model.entity.individual.pac.person;

import java.awt.*;

public final class SuperPac extends PacPerson {

    public static final double SUPER_WEIGHT = 0.0;

    public SuperPac(Color color) {
        super(color);
        weight = SUPER_WEIGHT;
    }
}
