package model.game;

import model.game.food.Food;
import model.game.food.PacGum;
import model.game.food.ability.Lightning;
import model.game.food.ability.Star;
import model.game.food.ability.SuperPacGum;
import model.game.food.fruit.*;
import model.game.grid.Grid;
import model.game.player.Player;

import java.util.HashSet;
import java.util.Set;

public final class Game {
    private final Set<Player> players;
    private final Grid grid;

    public Game() {
        players = new HashSet<>();
        grid = null;
    }

    private static Set<Food> foodsMultiplayer() {

        var result = foodsSingle();

        result.add(new Lightning());
        result.add(new Star());

        return result;
    }


    private static Set<Food> foodsSingle() {
        return Set.of(
                new Apple(),
                new Banana(),
                new Cherry(),
                new Melon(),
                new Orange(),
                new Peach(),
                new Pear(),
                new Strawberry(),
                new PacGum(),
                new SuperPacGum()
        );
    }
}
