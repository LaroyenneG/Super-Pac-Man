package model.game.grid;

import model.game.grid.square.Space;
import model.game.grid.square.Square;
import model.game.grid.square.Wall;
import model.game.grid.square.door.Door;
import model.game.grid.square.door.HauntedDoor;
import model.game.grid.square.door.PacDoor;
import stdlib.StdRandom;

import java.awt.*;
import java.util.HashSet;

public final class GridGenerator {

    private static final int MIN_GRID_SIZE = 20;



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

    private static boolean inMiddleArea(int x, int y, int size) {

        var area = buildAreas(size);

        var max = size * 1 / 3;
        var min = size * 2 / 3;

        return x > max && x < min && y > max && y < min;
    }

    public static Grid generate(int size) {

        if (size < MIN_GRID_SIZE) throw new IllegalArgumentException("Invalid grid size < " + MIN_GRID_SIZE);

        var result = buildKernel(size);

        var labyrinth = new int[size][size];
        assert labyrinth.length == result.length;
        assert labyrinth[0].length == result[0].length;

        var counter = 1;

        for (var i = 0; i < result.length; i++) {
            for (var j = 0; j < result[i].length; j++) {
                var origin = result[i][j];
                if (origin instanceof Wall) {
                    labyrinth[i][j] = 0;
                } else if (origin instanceof Door || inMiddleArea(j, i, size) || (i % 2 == 0 && j % 2 == 0 && StdRandom.bernoulli(0.5))) {
                    labyrinth[i][j] = counter++;
                } else {
                    labyrinth[i][j] = -1;
                }
            }
        }

        var values = new HashSet<>();

        do {
            var y = StdRandom.uniformInt(labyrinth.length);
            var x = StdRandom.uniformInt(labyrinth[y].length);

            var value = labyrinth[y][x];
            if (value < 0) {
                labyrinth[y][x] = counter++;
            } else if (value > 0 && !inMiddleArea(x, y, size) && StdRandom.bernoulli(0.4)) {
                labyrinth[y][x] = -1;
            }

            var novelized = false;

            do {
                novelized = true;

                for (var i = 0; i < labyrinth.length; i++) {
                    for (var j = 0; j < labyrinth[i].length; j++) {

                        var position = new Point(j, i);

                        if (labyrinth[position.y][position.x] <= 0) continue;

                        var neighbors = new HashSet<Point>();
                        neighbors.add(new Point(position.x + 1, position.y));
                        neighbors.add(new Point(position.x - 1, position.y));
                        neighbors.add(new Point(position.x, position.y + 1));
                        neighbors.add(new Point(position.x, position.y - 1));

                        for (var neighbor : neighbors) {
                            if (!(neighbor.y >= 0 && neighbor.y < labyrinth.length &&
                                    neighbor.x >= 0 && neighbor.x < labyrinth[i].length)) {
                                continue;
                            }

                            if (labyrinth[neighbor.y][neighbor.x] <= 0) continue;

                            var max = Math.max(labyrinth[position.y][position.x], labyrinth[neighbor.y][neighbor.x]);

                            if (labyrinth[neighbor.y][neighbor.x] != max || labyrinth[position.y][position.x] != max) {
                                labyrinth[neighbor.y][neighbor.x] = max;
                                labyrinth[position.y][position.x] = max;
                                novelized = false;
                                break;
                            }
                        }
                    }
                }
            } while (!novelized);

            values.clear();
            for (int[] line : labyrinth) {
                for (var v : line) {
                    values.add(v);
                }
            }
        } while (values.size() > 3);

        for (var i = 0; i < result.length; i++) {
            for (var j = 0; j < result[i].length; j++) {
                if (labyrinth[i][j] < 0) {
                    result[i][j] = new Wall();
                }
            }
        }

        return new Grid(result);
    }

    public static void main(String[] args) {
        System.out.println(new Grid(buildKernel(20)));
        System.out.println(generate(25));
    }
}
