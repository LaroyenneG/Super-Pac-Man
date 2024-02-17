package model;

import java.awt.*;

public final class Player {
    public static final Color DEFAULT_COLOR = Color.YELLOW;

    private final String name;
    private final Color color;
    private final Joystick joystick;
    private int score;


    public Player(String name, Color color) {
        this.joystick = new Joystick();
        this.name = name;
        this.color = color;
        score = 0;
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

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        score += value;
    }
}
