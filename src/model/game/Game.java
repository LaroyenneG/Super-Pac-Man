package model.game;

import model.game.entity.food.Food;
import model.game.entity.food.PacGum;
import model.game.entity.food.ability.Lightning;
import model.game.entity.food.ability.Star;
import model.game.entity.food.ability.SuperPacGum;
import model.game.entity.food.fruit.*;
import model.game.grid.Grid;
import model.game.grid.GridGenerator;

import java.util.HashSet;
import java.util.Set;

public final class Game {
    private final Set<Player> players;
    private final Grid grid;

    private long turn;

    public Game(int size) {
        turn = 0;
        players = new HashSet<>();
        grid = GridGenerator.generate(size);
    }

    public void nextTurn() {
        grid.moveIndividuals(turn);
        turn++;
    }

    public boolean isGameOver() {
        return false;
    }
    public Grid getGrid() {
        return grid;
    }

    public static Set<Class<? extends Food>> foodsMultiplayer() {

        var result = foodsSingle();

        result.add(Lightning.class);
        result.add(Star.class);

        return result;
    }


    public static Set<Class<? extends Food>> foodsSingle() {
        return Set.of(
                Apple.class,
                Banana.class,
                Cherry.class,
                Melon.class,
                Orange.class,
                Peach.class,
                Pear.class,
                Strawberry.class,
                PacGum.class,
                SuperPacGum.class
        );
    }
}
