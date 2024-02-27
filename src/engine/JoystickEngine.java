package engine;

import model.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JoystickEngine implements KeyListener {

    private final int[] keyEvents;
    private final Game game;
    private final int player;

    public JoystickEngine(int player, Game game, int... keyEvents) {
        assert keyEvents.length == 4;
        this.keyEvents = keyEvents;
        this.game = game;
        this.player = player;
    }

    public JoystickEngine(int player, Game game) {
        this(player, game, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        synchronized (game) {
            var players = game.getPlayers();
            assert players.length > player;
            var joystick = players[player].getJoystick();

            var keyCode = e.getKeyCode();
            if (keyEvents[0] == keyCode) {
                joystick.arrowKeyUpPressed();
            } else if (keyEvents[1] == keyCode) {
                joystick.arrowKeyRightPressed();
            } else if (keyEvents[2] == keyCode) {
                joystick.arrowKeyDownPressed();
            } else if (keyEvents[3] == keyCode) {
                joystick.arrowKeyLeftPressed();
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
