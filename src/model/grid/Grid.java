package model.grid;

import aud.SoundMachine;
import model.GameAbilityInterface;
import model.Player;
import model.entity.Entity;
import model.entity.food.Food;
import model.entity.food.ability.Ability;
import model.entity.individual.Individual;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;
import model.grid.square.Square;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Grid  {
    private final Set<PacPerson> pacPeople;
    private final Set<Ghost> ghosts;
    private final Set<Food> foods;
    private final Square[][] squares;

    public Grid(Square[][] squares) {
        this.squares = squares;
        foods = new HashSet<>();
        ghosts = new HashSet<>();
        pacPeople = new HashSet<>();
    }

    public void addEntity(Ghost ghost) {
        ghosts.add(ghost);
    }

    public void addEntity(PacPerson pacPerson) {
        pacPeople.add(pacPerson);
    }

    public void addEntity(Food food) {
        foods.add(food);
    }

    public void removeEntity(PacPerson pacPerson) {
        pacPeople.remove(pacPerson);
    }

    @Override
    public String toString() {

        var result = new StringBuilder();

        for (var line : squares) {
            for (var colum : line) {
                result.append(colum);
                result.append(' ');
            }
            result.append("\n");
        }

        return result.toString();
    }

    public Square[][] getSquares() {
        return squares;
    }

    public int size() {
        return squares.length;
    }

    public Set<Individual> getIndividuals() {
        var result = new HashSet<Individual>();
        result.addAll(ghosts);
        result.addAll(pacPeople);
        return result;
    }

    public Set<Entity> getEntities() {
        var result = new HashSet<Entity>();
        result.addAll(getIndividuals());
        result.addAll(foods);
        return result;
    }

    public Set<Ghost> getGhosts() {
        return ghosts;
    }

    public Set<PacPerson> getPacPeople() {
        return pacPeople;
    }

    public PacPerson findPacPerson(Color color) {

        PacPerson result = null;

        for (var pacPerson : pacPeople) {
            var pacPersonColor = pacPerson.getColor();
            if (Objects.equals(pacPersonColor, color)) {
                result = pacPerson;
                break;
            }
        }

        return result;
    }

    public Set<Food> getFoods() {
        return foods;
    }


    public Point ghostHomePosition() {
        var gridSize = size();
        return new Point(gridSize / 2, gridSize / 2 - 1);
    }

    public Point pacPeopleHomePosition() {
        var gridSize = size();
        return new Point(gridSize / 2, gridSize / 2 + 1);
    }

    private Square finSquare(Point position) {
        if (position.y < 0 || position.y >= squares.length) return null;
        if (position.x < 0 || position.x >= squares[position.y].length) return null;
        return squares[position.y][position.x];
    }

    public boolean accept(Ghost ghost, Point position) {

        var square = finSquare(position);
        if (square == null) return false;

        return square.accept(ghost);
    }

    public boolean accept(Food food, Point position) {

        var square = finSquare(position);
        if (square == null) return false;

        return square.accept(food);
    }


    public boolean accept(PacPerson pacPerson, Point position) {

        var square = finSquare(position);
        if (square == null) return false;

        return square.accept(pacPerson);
    }

    public void eats(GameAbilityInterface game, PacPerson pacPerson, Player player) {
        for(var food : foods) {
            if(Objects.equals(food.getPosition(), pacPerson.getPosition())) {
                if(pacPerson.eats(food)) {
                    foods.remove(food);
                    var points = food.getPoints();
                    player.addScore(points);
                    SoundMachine.getInstance().playEatGum();
                    if(food instanceof Ability ability) {
                        ability.apply(pacPerson, game);
                    }
                    break;
                }
            }
        }
    }
}
