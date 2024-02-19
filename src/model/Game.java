package model;

import model.entity.food.Food;
import model.entity.food.PacGum;
import model.entity.food.ability.Lightning;
import model.entity.food.ability.Star;
import model.entity.food.ability.SuperPacGum;
import model.entity.food.ability.Trident;
import model.entity.food.fruit.*;
import model.entity.individual.ghost.*;
import model.entity.individual.pac.person.PacMan;
import model.grid.Grid;
import model.grid.GridGenerator;
import stdlib.StdRandom;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Game {
    private final Player[] players;
    private final Grid grid;
    private final Set<Class<? extends Food>> foodClasses;
    private long turn;


    public Game(int size, Player... players) {
        this.players = players;
        this.foodClasses = new HashSet<>();
        grid = GridGenerator.generate(size);
        turn = 0;
        buildFoodClasses();
        buildGhosts();
        buildPacPeople();
        buildFoods();
    }

    private void buildFoods() {
        for (var i = 0; i < Math.pow(grid.size(), 2); i++) {
            generateNewFood();
        }
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
        controlPacPeople();
        controlGhosts();
        grid.moveIndividuals(turn);
        generateNewFood();
        turn++;
    }

    private void controlPacPeople() {

        var playerCopy = players.clone();
        StdRandom.shuffle(playerCopy);

        for (var player : playerCopy) {
            var color = player.getColor();
            var joystick = player.getJoystick();
            var heading = joystick.getPosition();
            var pacPerson = grid.finPacPerson(color);
            pacPerson.setHeading(heading);
        }
    }

    private void controlGhosts() {

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

    public void buildPacPeople() {
        var homePosition = grid.pacPeopleHomePosition();
        for (var i = 0; i < players.length; i++) {
            var color = players[i].getColor();
            var pacMan = new PacMan(color);
            pacMan.setPosition(new Point(homePosition.x - players.length / 2 + i, homePosition.y));
            grid.addEntity(pacMan);
        }
    }

    public void generateNewFood() {

        var position = new Point(StdRandom.uniformInt(grid.size()), StdRandom.uniformInt(grid.size()));

        var index = 0;
        var foods = new Food[foodClasses.size()];
        for (var foodClass : foodClasses) {
            try {
                foods[index++] = (foodClass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        StdRandom.shuffle(foods);

        var random = StdRandom.uniformDouble();
        Food randomFood = null;
        for (var food : Arrays.stream(foods).sorted((o1, o2) -> (int) ((o1.getProbability() - o2.getProbability()) * Float.MAX_VALUE)).toList()) {
            var probability = food.getProbability();
            if (probability > random) {
                randomFood = food;
                break;
            }
        }

        if (randomFood != null) {
            var existingEntity = grid.getEntities();
            if (grid.accept(randomFood, position) &&
                    existingEntity.stream().noneMatch(food -> Objects.equals(food.getPosition(), position))) {
                if (randomFood.isUnique()) {
                    foodClasses.remove(randomFood.getClass());
                }
                randomFood.setPosition(position);
                grid.addEntity(randomFood);
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}
