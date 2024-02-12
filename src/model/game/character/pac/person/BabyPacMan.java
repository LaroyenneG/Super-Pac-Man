package model.game.character.pac.person;

import java.awt.*;

public final class BabyPacMan extends PacPerson {
    public BabyPacMan(Color color) {
        super(color);
    }

    public BabyPacMan() {
        this(DEFAULT_COLOR);
    }
}
