package model.game.controller;

import model.game.Heading;

public final class Joystick {
    private Heading position;

    public Joystick() {
        position = null;
    }

    public void arrowKeyUpPressed() {
        position = Heading.UP;
    }

    public void arrowKeyDownPressed() {
        position = Heading.DOWN;
    }

    public void arrowKeyRightPressed() {
        position = Heading.RIGHT;
    }

    public void arrowKeyLeftPressed() {
        position = Heading.LEFT;
    }

    public Heading getPosition() {
        return position;
    }
}
