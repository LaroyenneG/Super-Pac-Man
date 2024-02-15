package engine;

import gui.GridDraftsman;
import model.game.Game;
import model.game.Joystick;
import stdlib.StdDraw;

import javax.swing.*;

public final class GameEngine implements Runnable {

    private static final int FREQUENCY = 200;

    private final Thread thread;

    private final Game game;


    public GameEngine(Game game) {
        thread = new Thread(this);
        this.game = game;
    }

    @Override
    public void run() {
        try {
            var grid = game.getGrid();
            var size = grid.getSize();
            var joystick = new Joystick();
            var gridDraftsman = new GridDraftsman(size);
            var joystickEngine = new JoystickEngine(joystick);

            SwingUtilities.invokeLater(() -> {
                gridDraftsman.init();
                StdDraw.addKeyListener(joystickEngine);
            });

            SwingUtilities.invokeLater(() -> gridDraftsman.draw(grid));

            while (!game.isGameOver()) {

                var start = System.currentTimeMillis();

                joystickEngine.invoke(game::nextTurn);

                SwingUtilities.invokeLater(() -> gridDraftsman.draw(grid));
                SwingUtilities.invokeLater(() -> {
                    // GUI interface
                });

                var end = System.currentTimeMillis();

                Thread.sleep(FREQUENCY - (end - start));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        thread.start();
    }
}
