package engine;

import model.game.Joystick;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JoystickEngine implements KeyListener {

    private final Joystick joystick;

    private final Object mutex;


    public JoystickEngine(Joystick joystick) {
        mutex = new Object();
        this.joystick = joystick;
    }

    public void invoke(Runnable runnable) {
        synchronized (mutex) {
            runnable.run();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        synchronized (mutex) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    joystick.arrowKeyUpPressed();
                    break;
                case KeyEvent.VK_DOWN:
                    joystick.arrowKeyDownPressed();
                    break;
                case KeyEvent.VK_RIGHT:
                    joystick.arrowKeyRightPressed();
                    break;
                case KeyEvent.VK_LEFT:
                    joystick.arrowKeyLeftPressed();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
