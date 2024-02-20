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
            var players = game.getPlayers();
            assert players.length > 0;
            var human = players[0];
            var joystick = human.getJoystick();
            var gridDraftsman = new GameDraftsman(size);
            var joystickEngine = new JoystickEngine(joystick);

            SwingUtilities.invokeLater(() -> {
                gridDraftsman.init();
                StdDraw.addKeyListener(joystickEngine);
            });

            SwingUtilities.invokeLater(() -> gridDraftsman.draw(game));

            var soundMachine = SoundMachine.getInstance();
            soundMachine.playStart();

            while (!game.isGameOver()) {

                var start = System.currentTimeMillis();

                joystickEngine.invoke(game::nextTurn);

                SwingUtilities.invokeLater(() -> gridDraftsman.draw(game));

                var end = System.currentTimeMillis();

                var waitingTime = Math.max(FREQUENCY - (end - start), 0);
                maxLatency = Math.max((end - start) * 100.0 / FREQUENCY, maxLatency);
                Thread.sleep(waitingTime);
            }

            System.out.println("Max latency : " + maxLatency + "%");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        thread.start();
    }
}
