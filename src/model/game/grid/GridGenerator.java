package model.game.grid;

import ai.Compute;
import model.game.grid.square.Space;
import model.game.grid.square.Square;
import model.game.grid.square.Wall;
import model.game.grid.square.door.HauntedDoor;
import model.game.grid.square.door.PacDoor;
import stdlib.StdRandom;

import java.awt.*;
import java.util.HashSet;

public final class GridGenerator {

    private static final int MIN_GRID_SIZE = 30;

    private static class InvalidGridException extends Exception {
        public InvalidGridException() {
            super("Not available grid");
        }
    }


    private GridGenerator() {
    }


    private static Square[][] buildAreas(int size) {

        var result = buildEmpty(size, size - 1);

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


    private static Square[][] buildEmpty(int x, int y) {

        var result = new Square[y][x];

        for (var i = 0; i < result.length; i++) {
            for (var j = 0; j < result[i].length; j++) {
                result[i][j] = new Space();
            }
        }

        return result;
    }


    private static Square[][] buildKernel(int size) {

        var result = buildEmpty(size, size);
        var areas = buildAreas(Math.min(10, Math.max(8, size / 4)));

        assert areas.length >= 1;

        var areasY = 0;
        var areasX = 0;

        for (var i = 0; i < result.length; i++) {
            for (var j = 0; j < result[i].length; j++) {
                if (i + areas.length / 2 == result.length / 2 &&
                        j + areas[0].length / 2 == result[i].length / 2) {
                    areasY = i;
                    areasX = j;
                }
                if (i == 0 || j == 0 || i == result.length - 1 || j == result[i].length - 1) {
                    result[i][j] = new Wall();
                }
            }
        }

        assert areasY + areas.length < result.length;
        assert areasX + areas[0].length < result.length;

        for (var i = 0; i < areas.length; i++) {
            System.arraycopy(areas[i], 0, result[areasY + i], areasX, areas[i].length);
        }


        return result;
    }


    public static Grid generate(int size) {

        if (size < MIN_GRID_SIZE) throw new IllegalArgumentException("Invalid grid size < " + MIN_GRID_SIZE);

        var result = buildKernel(size);

        for (var i = 0; i < result.length; i++) {
            for (var j = 0; j < result[i].length; j++) {
                var origin = result[i][j];
                if (origin.isImpassable() || StdRandom.bernoulli()) {
                    continue;
                }

                result[i][j] = new Wall();

                try {
                    for (var k = 0; k < result.length; k++) {
                        for (var l = 0; l < result[k].length; l++) {

                            var from = result[k][l];
                            if (from.isImpassable()) {
                                continue;
                            }

                            for (var m = 0; m < result.length; m++) {
                                for (var n = 0; n < result[m].length; n++) {

                                    var to = result[m][n];
                                    if (to.isImpassable()) {
                                        continue;
                                    }

                                    var way = Compute.findWay(result, new Point(k, l), new Point(m, n));
                                    if (way == null) {
                                        throw new InvalidGridException();
                                    }
                                }
                            }
                        }
                    }
                } catch (InvalidGridException e) {
                    result[i][j] = origin;
                }
            }
        }

        return new Grid(result);
    }

    public static void main(String[] args) {
        System.out.println(new Grid(buildKernel(30)));
        System.out.println(generate(40));
    }
}
