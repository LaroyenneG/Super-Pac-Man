package model.game;

import model.game.grid.Grid;
import model.game.player.Player;

public final class Game {

    private int level = 0;

    private final Player[] players;

    private final Grid grid;


    public Game() {
        players = null;
        grid = null;
    }
}
