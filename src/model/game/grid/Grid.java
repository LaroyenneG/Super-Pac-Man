package model.game.grid;

import model.game.entity.Entity;
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

    public Set<Food> getFoods() {
        return foods;
    }

    public void moveIndividuals(long turn) {

    }


    public Point ghostHomePosition() {
        var gridSize = size();
        return new Point(gridSize / 2, gridSize / 2 - 1);
    }

    public Set<Point> freeSpaces() {

        var result = new HashSet<Point>();

        var entities = getEntities();
        for (var i = 0; i < squares.length; i++) {
            for (var j = 0; j < squares[i].length; j++) {
                var available = false;
                if (squares[i][j].isFree()) {
                    available = true;
                    for (var entity : entities) {
                        var position = entity.getPosition();
                        if (position.y == i && position.x == j) {
                            available = false;
                            break;
                        }
                    }
                }
                if (available) {
                    result.add(new Point(j, i));
                }
            }
        }


        return result;
    }
}
