package model.game.grid;

import model.game.grid.square.Square;

public class Grid {

    private final Square[][] squares;


    public Grid(Square[][] squares) {
        this.squares = squares;
    }

    @Override
    public String toString() {

        var result = new StringBuilder();

        for (var line : squares) {
            for (var colum : line) {
                result.append(colum);
            }
            result.append("\n");
        }

        return result.toString();
    }
}
