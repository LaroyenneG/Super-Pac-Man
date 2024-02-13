package model.game;

import model.game.food.Food;
import model.game.food.PacGum;
import model.game.food.ability.Lightning;
import model.game.food.ability.Star;
import model.game.food.ability.SuperPacGum;
import model.game.food.fruit.*;
import model.game.grid.Grid;
import model.game.grid.GridGenerator;

import java.util.HashSet;
import java.util.Set;

public final class Game {
    private final Set<Player> players;
    private final Grid grid;

    public Game(int size) {
        players = new HashSet<>();
        grid = GridGenerator.generate(size);
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
