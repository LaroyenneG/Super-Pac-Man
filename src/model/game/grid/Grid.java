package model.game.grid;

import model.game.character.Character;
import model.game.food.Food;
import model.game.grid.square.Passable;
import model.game.grid.square.Space;
import model.game.grid.square.Square;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    private final Map<Passable, Character> characters;
    private final Map<Space, Food> foods;
    private final Square[][] squares;

    public Grid(Square[][] squares) {
        this.squares = squares;
        foods = new HashMap<>();
        characters = new HashMap<>();
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
}
