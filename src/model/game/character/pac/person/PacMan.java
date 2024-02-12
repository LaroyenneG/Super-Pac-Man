package model.game.character.pac.person;

import java.awt.*;

public final class PacMan extends PacPerson {

    public PacMan(Color color) {
        super(color);
    }

    public PacMan() {
        this(DEFAULT_COLOR);
    }
}
