package model.game;

import model.game.entity.individual.pac.person.JrPacMan;
import model.game.entity.individual.pac.person.PacPerson;

import java.awt.*;

public final class Player {
    public static final Color DEFAULT_COLOR = Color.YELLOW;

    private final String name;
    private final Color color;
    private final Joystick joystick;

    private int score;
    private PacPerson avatar;


    public Player(String name, Color color) {
        this.joystick = new Joystick();
        avatar = new JrPacMan();
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

    public PacPerson getAvatar() {
        return avatar;
    }

    public void setAvatar(PacPerson avatar) {
        this.avatar = avatar;
    }
}
