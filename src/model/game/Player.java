package model.game;

import model.game.entity.character.pac.person.PacPerson;

import java.awt.*;

public final class Player {

    private static final Color DEFAULT_COLOR = Color.YELLOW;
    private static final int DEFAULT_SCORE = 0;

    private final String name;
    private final Color color;
    private int score;
    private PacPerson avatar;

    private final Joystick joystick = null;


    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        score = DEFAULT_SCORE;
        avatar = null;
    }

    public Player(String name) {
        this(name, DEFAULT_COLOR);
    }
}
