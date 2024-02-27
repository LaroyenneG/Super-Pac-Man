package engine;

import aud.SoundMachine;
import gui.GameDraftsman;
import model.Game;
import stdlib.StdDraw;

import javax.swing.*;

public final class GameEngine implements Runnable {
    public static final int FREQUENCY = 50;

    private final Thread thread;

    private final Game game;

    public GameEngine(Game game) {
        thread = new Thread(this);
        this.game = game;
    }

    @Override
    public void run() {

        var maxLatency = 0.0;

        try {
            var grid = game.getGrid();
            var size = grid.size();
            var gridDraftsman = new GameDraftsman(size);
            var joystickEngine = new JoystickEngine(0, game);

            SwingUtilities.invokeLater(() -> {
                gridDraftsman.init();
                synchronized (game) {
                    StdDraw.addKeyListener(joystickEngine);
                    gridDraftsman.draw(game);
                }
            });

            var soundMachine = SoundMachine.getInstance();
            soundMachine.playStart();

            var gameOver = false;

            while (!gameOver) {

                var start = System.currentTimeMillis();

                synchronized (game) {
                    game.nextTurn();
                    gameOver = game.isGameOver();
                }

                SwingUtilities.invokeLater(() -> {
                    synchronized (game) {
                        gridDraftsman.draw(game);
                    }
                });

                var end = System.currentTimeMillis();

                var waitingTime = Math.max(FREQUENCY - (end - start), 0);
                maxLatency = Math.max((end - start) * 100.0 / FREQUENCY, maxLatency);
                Thread.sleep(waitingTime);
            }

            System.out.println("Max latency recorded : " + maxLatency + "%");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        thread.start();
    }
}
