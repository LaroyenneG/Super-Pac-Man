package model.game;

import model.game.entity.individual.pac.person.PacPerson;

import java.awt.*;

public final class Player {

    private static final Color DEFAULT_COLOR = Color.YELLOW;

    private final String name;
    private final Color color;
    private int score;
    private PacPerson avatar;

    private final Joystick joystick;


    public Player(String name, Color color) {
        this.joystick = new Joystick();
        this.name = name;
        this.color = color;
        score = 0;
        avatar = null;
    }

    public Player(String name) {
        this(name, DEFAULT_COLOR);
    }

    public Joystick getJoystick() {
        return joystick;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
