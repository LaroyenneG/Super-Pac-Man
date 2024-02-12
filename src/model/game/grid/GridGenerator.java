package model.game.grid;

import model.game.grid.square.Space;
import model.game.grid.square.Square;
import model.game.grid.square.Wall;
import model.game.grid.square.door.HauntedDoor;
import model.game.grid.square.door.PacDoor;

public final class GridGenerator {

    private static final int MIN_GRID_SIZE = 30;


    private GridGenerator() {
    }


    private static Square[][] buildCenterArea(int size) {

        var result = buildKernel(size, size - 1);

        for (var i = 0; i < result.length; i++) {
            for (var j = 0; j < result[i].length; j++) {

                var isTop = i == 0;
                var isBottom = i == result.length - 1;
                var isMiddle = i == result.length / 2;
                var isLeftOrRight = j == 0 || j == result[i].length - 1;
                var isDoorColum = j == result[i].length / 2 || j + 1 == result[i].length / 2;

                if (isTop && isDoorColum) {
                    result[i][j] = new HauntedDoor();
                } else if (isBottom && isDoorColum) {
                    result[i][j] = new PacDoor();
                } else if (isTop || isBottom || isMiddle || isLeftOrRight) {
                    result[i][j] = new Wall();
                }
            }
        }

        return result;
    }


    private static Square[][] buildKernel(int x, int y) {

        var result = new Square[y][x];

        for (var i = 0; i < result.length; i++) {
            for (var j = 0; j < result[i].length; j++) {
                result[i][j] = new Space();
            }
        }

        return result;
    }

    public static Grid generate(int size) {

        if (size < MIN_GRID_SIZE) throw new IllegalArgumentException("Invalid grid size < " + MIN_GRID_SIZE);

        var squares = buildKernel(size, size);

        return new Grid(squares);
    }

    public static void main(String[] args) {
        System.out.println(new Grid(buildCenterArea(10)));
    }
}
