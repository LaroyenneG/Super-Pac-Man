package model.game.grid;

import model.game.entity.Entity;
import model.game.entity.food.Food;
import model.game.entity.individual.Individual;
import model.game.entity.individual.ghost.Blinky;
import model.game.entity.individual.ghost.Ghost;
import model.game.entity.individual.ghost.Pinky;
import model.game.grid.square.Passable;
import model.game.grid.square.Space;
import model.game.grid.square.Square;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grid {
    private final Set<Individual> individuals;
    private final Set<Food> foods;
    private final Square[][] squares;

    public Grid(Square[][] squares) {
        this.squares = squares;
        foods = new HashSet<>();
        individuals = new HashSet<>(Set.of(new Blinky(), new Pinky()));
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

    public int getSize() {
        return squares.length;
    }

    public Set<Individual> getIndividuals() {
        return new HashSet<>(Set.of(new Blinky(), new Pinky()));
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public void moveIndividuals(long turn) {

    }
}
