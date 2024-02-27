package model;

import ai.Compute;
import aud.SoundMachine;
import model.entity.food.Food;
import model.entity.food.PacGum;
import model.entity.food.ability.Lightning;
import model.entity.food.ability.Star;
import model.entity.food.ability.SuperPacGum;
import model.entity.food.ability.Trident;
import model.entity.food.fruit.*;
import model.entity.individual.Individual;
import model.entity.individual.ghost.*;
import model.entity.individual.pac.person.PacMan;
import model.entity.individual.pac.person.PacPerson;
import model.grid.Grid;
import model.grid.GridGenerator;
import stdlib.StdRandom;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class Game implements GameAbilityInterface {

    private final Player[] players;
    private final Grid grid;
    private final Set<Class<? extends Food>> foodClasses;
    private long turn;

    private final Map<Individual, MotionState> individualMotionStateMap;

    private final Set<ScheduledTask> scheduledTasks;


    public Game(int size, Player... players) {
        this.players = players;
        individualMotionStateMap = new HashMap<>();
        scheduledTasks = new HashSet<>();
        foodClasses = new HashSet<>();
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

        var result = true;

        var pacPeople = grid.getPacPeople();
        for (var pacPerson : pacPeople) {
            if (pacPerson.isAlive()) {
                result = false;
                break;
            }
        }

        return result;
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
        executeScheduledTasks();
        controlPacPeople();
        controlGhosts();
        generateNewFood();
        turn++;
    }

    private void executeScheduledTasks() {

        var executedTasks = new HashSet<ScheduledTask>();

        var currentTime = System.currentTimeMillis();

        for (var scheduledTask : scheduledTasks) {
            var moment = scheduledTask.moment();
            if (moment <= currentTime) {
                var task = scheduledTask.task();
                task.run();
                executedTasks.add(scheduledTask);
            }
        }

        scheduledTasks.removeAll(executedTasks);
    }

    private boolean individualCanBeMoved(Individual individual) {
        return Math.round(turn % Math.round(computeSpeedRateTurn(individual))) == 0;
    }

    private void controlPacPeople() {

        var playerCopy = players.clone();
        StdRandom.shuffle(playerCopy);

        for (var player : playerCopy) {
            var color = player.getColor();
            var pacPerson = grid.findPacPerson(color);
            var targetPosition = pacPerson.targetPosition();
            var joystick = player.getJoystick();
            var heading = joystick.getPosition();

            if (individualCanBeMoved(pacPerson)) {
                var motionState = individualMotionStateMap.getOrDefault(pacPerson, MotionState.HEADING);
                switch (motionState) {
                    case HEADING:
                        pacPerson.setHeading(heading);
                        pacPerson.setMoving(false);
                        motionState = MotionState.TRANSITION;
                        break;
                    case TRANSITION:
                        if (grid.accept(pacPerson, targetPosition)) {
                            pacPerson.setMoving(true);
                            motionState = MotionState.MOVE;
                        } else {
                            motionState = MotionState.HEADING;
                        }
                        break;
                    case MOVE:
                        if (grid.accept(pacPerson, targetPosition)) {
                            pacPerson.move();
                            grid.eats(this, pacPerson, player);
                        }
                        motionState = MotionState.HEADING;
                        break;
                }

                individualMotionStateMap.put(pacPerson, motionState);
            }
        }
    }

    private void controlGhosts() {

        var ghosts = grid.getGhosts();

        for (var ghost : ghosts) {
            if (individualCanBeMoved(ghost)) {
                var targetPosition = ghost.targetPosition();
                var heading = Compute.ghostHeading(ghost, grid);
                var motionState = individualMotionStateMap.getOrDefault(ghost, MotionState.HEADING);
                switch (motionState) {
                    case HEADING:
                        ghost.setHeading(heading);
                        ghost.setMoving(false);
                        motionState = MotionState.TRANSITION;
                        break;
                    case TRANSITION:
                        if (grid.accept(ghost, targetPosition)) {
                            ghost.setMoving(true);
                            motionState = MotionState.MOVE;
                        } else {
                            motionState = MotionState.HEADING;
                        }
                        break;
                    case MOVE:
                        if (grid.accept(ghost, targetPosition)) {
                            ghost.move();
                        }
                        motionState = MotionState.HEADING;
                        break;
                }
                individualMotionStateMap.put(ghost, motionState);
            }


            for (var pacPerson : grid.getPacPeople()) {
                if (pacPerson.isGiant()) {
                    continue;
                }

                if (Objects.equals(pacPerson.getPosition(), ghost.getPosition())) {
                    pacPerson.kill();
                    SoundMachine.getInstance().playDeath();
                }
            }
        }
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

        var foodsAlreadyPresent = grid.getFoods();
        if (foodsAlreadyPresent.size() > grid.size() * 10.0) return;

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

    public static final double MAX_SPEED = 50.0;

    private static double computeSpeedRateTurn(Individual individual) {
        return Math.max((1.0 + (1.0 - individual.speed() / MAX_SPEED) * 3.0), 1.0);
    }

    @Override
    public void evolve(PacPerson pacPerson) {

        var color = pacPerson.getColor();

        var from = grid.findPacPerson(color);
        if (from != null) {
            grid.addEntity(pacPerson);
            grid.removeEntity(from);

            var taskId = ScheduledTask.EVOLVE_TASK_PREFIX + pacPerson;
            var now = System.currentTimeMillis();
            var scheduledTask = new ScheduledTask(taskId, now + ScheduledTask.TASK_DEFAULT_DURATION, () -> {
                grid.addEntity(from);
                grid.removeEntity(pacPerson);
            });

            cancelScheduledTask(taskId);
            scheduledTasks.add(scheduledTask);
        }
    }

    @Override
    public void scareOffGhosts() {

        var ghosts = grid.getGhosts();

        for (var ghost : ghosts) {
            ghost.scareOff();
        }

        var now = System.currentTimeMillis();
        var scheduledTask = new ScheduledTask(ScheduledTask.SCARE_OFF_GHOSTS_TASK, now + ScheduledTask.TASK_DEFAULT_DURATION, () -> {
            for (var ghost : ghosts) {
                ghost.reassure();
            }
        });

        cancelScheduledTask(ScheduledTask.SCARE_OFF_GHOSTS_TASK);
        scheduledTasks.add(scheduledTask);
    }

    @Override
    public void miniaturizePacPeople() {

        var pacPeople = grid.getPacPeople();

        var toEvolves = new HashSet<PacPerson>();

        for (var pacPerson : pacPeople) {
            cancelScheduledTask(ScheduledTask.EVOLVE_TASK_PREFIX + pacPerson);
            toEvolves.add(new PacMan(pacPerson));
        }

        for (var toEvolve : toEvolves) {
            evolve(toEvolve);
        }
    }

    private void cancelScheduledTask(String id) {

        var canceledTasks = new HashSet<ScheduledTask>();

        for (var scheduledTask : scheduledTasks) {
            if (Objects.equals(scheduledTask.id(), id)) {
                canceledTasks.add(scheduledTask);
            }
        }

        scheduledTasks.removeAll(canceledTasks);
    }
}
