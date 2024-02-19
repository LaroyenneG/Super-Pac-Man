package model.grid;

import model.entity.Entity;
import model.entity.food.Food;
import model.entity.individual.Individual;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;
import model.grid.square.Square;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
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

    public PacPerson finPacPerson(Color color) {

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
        assert position.x < squares.length;
        assert position.x >= 0;
        assert position.y < squares.length;
        assert position.y >= 0;
        return squares[position.y][position.x];
    }

    public boolean accept(Ghost ghost, Point position) {

        var square = finSquare(position);

        return square.accept(ghost);
    }

    public boolean accept(Food food, Point position) {

        var square = finSquare(position);

        return square.accept(food);
    }


    public boolean accept(PacPerson pacPerson, Point position) {

        var square = finSquare(position);

        return square.accept(pacPerson);
    }
}
