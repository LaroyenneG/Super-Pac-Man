package engine;

import model.Joystick;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JoystickEngine implements KeyListener {

    private final int[] keyEvents;
    private final Joystick joystick;
    private final Object mutex;


    public JoystickEngine(Joystick joystick, int... keyEvents) {
        assert keyEvents.length == 4;
        this.keyEvents = keyEvents;
        mutex = new Object();
        this.joystick = joystick;
    }

    public JoystickEngine(Joystick joystick) {
        this(joystick, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT);
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
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        var keyCode = e.getKeyCode();
        
        synchronized (mutex) {
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
