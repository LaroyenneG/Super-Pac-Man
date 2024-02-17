package model.game.grid;

import model.game.entity.food.Food;
import model.game.entity.individual.Individual;
import model.game.entity.individual.ghost.Ghost;
import model.game.entity.individual.pac.person.PacPerson;
import model.game.grid.square.Square;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Grid {
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

    public Set<Ghost> getGhosts() {
        return ghosts;
    }

    public Set<PacPerson> getPacPeople() {
        return pacPeople;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public void moveIndividuals(long turn) {

    }


    public Point ghostHomePosition() {
        var gridSize = size();
        return new Point(gridSize / 2, gridSize / 2 - 1);
    }
}
