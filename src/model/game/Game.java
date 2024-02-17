package model.game;

import model.game.entity.food.Food;
import model.game.entity.food.PacGum;
import model.game.entity.food.ability.Lightning;
import model.game.entity.food.ability.Star;
import model.game.entity.food.ability.SuperPacGum;
import model.game.entity.food.ability.Trident;
import model.game.entity.food.fruit.*;
import model.game.entity.individual.ghost.*;
import model.game.grid.Grid;
import model.game.grid.GridGenerator;
import stdlib.StdRandom;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class Game {

    private static final int FOOD_TURN = 10;

    private final Player[] players;
    private final Grid grid;

    private final Set<Class<? extends Food>> foodClasses;

    private long turn;


    public Game(int size, Player... players) {
        this.players = players;
        this.foodClasses = new HashSet<>();
        grid = GridGenerator.generate(size);
        turn = 0;
        buildGhosts();
        buildFoodClasses();
    }

    public static Set<Class<? extends Ghost>> ghosts() {
        return Set.of(
                Blinky.class,
                Clyde.class,
                Inky.class,
                Pinky.class
        );
    }

    public static Set<Class<? extends Food>> foodsMultiplayer() {
        var result = new HashSet<>(foodsSingle());
        result.add(Lightning.class);
        result.add(Star.class);
        result.add(Trident.class);
        return result;
    }

    public boolean isGameOver() {
        return false;
    }
    public Grid getGrid() {
        return grid;
    }

    public boolean isMultiplayer() {
        return players.length > 1;
    }

    private void buildFoodClasses() {
        foodClasses.addAll((isMultiplayer()) ? foodsMultiplayer() : foodsSingle());
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

    public void nextTurn() {
        grid.moveIndividuals(turn);
        if (turn % FOOD_TURN == 0) {
            generateNewFood();
        }
        turn++;
    }

    public void buildGhosts() {
        var classes = ghosts();
        var index = 0;
        var homePosition = grid.ghostHomePosition();
        for (var ghostClass : classes) {
            try {
                var ghost = ghostClass.getConstructor().newInstance();
                ghost.setPosition(new Point(homePosition.x - classes.size() / 2 + index++, homePosition.y));
                grid.addEntity(ghost);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateNewFood() {

        var freeSpaces = new ArrayList<>(grid.freeSpaces());
        if (freeSpaces.isEmpty()) {
            return;
        }
        var freeSpace = freeSpaces.get(StdRandom.uniformInt(freeSpaces.size()));

        var foods = new HashSet<Food>();
        for (var foodClass : foodClasses) {
            try {
                foods.add(foodClass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        var random = StdRandom.uniformDouble();

        for (var food : foods) {
            var probability = food.getProbability();
            if (probability > random) {
                if (food.isUnique()) {
                    foodClasses.remove(food.getClass());
                }
                food.setPosition(freeSpace);
                grid.addEntity(food);
                break;
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}
