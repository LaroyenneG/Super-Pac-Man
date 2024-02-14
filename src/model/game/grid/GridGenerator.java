package model.game.grid;

import gui.GridDraftsman;
import model.game.grid.square.Space;
import model.game.grid.square.Square;
import model.game.grid.square.Wall;
import model.game.grid.square.door.Door;
import model.game.grid.square.door.HauntedDoor;
import model.game.grid.square.door.PacDoor;
import stdlib.StdOut;
import stdlib.StdRandom;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public final class GridGenerator {

    private static final int MIN_GRID_SIZE = 20;

    private static final int ITERATIONS = 100;

    private static final int THREADS = Runtime.getRuntime().availableProcessors();


    private GridGenerator() {
    }


    private static Square[][] buildAreas(int size) {

        var result = buildEmpty(size, size / 2 + 1);

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

        var max = size / 3;
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
                } else if (origin instanceof Door || inMiddleArea(j, i, size) || (i % 2 == 0 && j % 2 == 0 && StdRandom.bernoulli(0.4))) {
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

                        var neighbors = findNeighbors(position, false);

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
            for (var l : labyrinth) {
                for (var c : l) {
                    values.add(c);
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

    private static HashSet<Point> findNeighbors(Point position, boolean diagonal) {
        var result = new HashSet<Point>();
        result.add(new Point(position.x + 1, position.y));
        result.add(new Point(position.x - 1, position.y));
        result.add(new Point(position.x, position.y + 1));
        result.add(new Point(position.x, position.y - 1));
        if (diagonal) {
            result.add(new Point(position.x - 1, position.y - 1));
            result.add(new Point(position.x + 1, position.y + 1));
        }

        return result;
    }

    private static int countBlocks(Square[][] squares) {

        var result = 0;

        for (var line : squares) {
            for (var value : line) {
                if (value.isImpassable()) {
                    result++;
                }
            }
        }

        return result;
    }

    private static int countHyperBlocks(Square[][] squares) {

        var result = 0;

        for (var i = 0; i < squares.length; i++) {
            for (var j = 0; j < squares[i].length; j++) {

                var neighborsCount = 0;

                var square = squares[i][j];
                if (!square.isImpassable()) {
                    continue;
                }

                var neighbors = findNeighbors(new Point(j, i), true);
                for (var neighbor : neighbors) {
                    if (!(neighbor.y >= 0 && neighbor.y < squares.length &&
                            neighbor.x >= 0 && neighbor.x < squares[i].length)) {
                        continue;
                    }

                    var nextSquare = squares[neighbor.y][neighbor.x];

                    if (!nextSquare.isImpassable()) {
                        continue;
                    }

                    neighborsCount++;
                }

                if (neighborsCount >= 4) {
                    result++;
                }
            }
        }

        return result;
    }


    private static Grid generateHyperGrid(int size) {

        Grid result = null;

        var gridScoreMap = Collections.synchronizedMap(new HashMap<Integer, Grid>());

        for (var i = 0; i < ITERATIONS; i++) {

            StdOut.println("Start iteration " + i);

            var threads = new HashSet<Thread>();
            for (var j = 0; j < THREADS; j++) {
                var thread = new Thread(() -> {
                    var grid = generate(size);
                    var block = countBlocks(grid.getSquares());
                    var hyperBlocks = countHyperBlocks(grid.getSquares());
                    var score = block - hyperBlocks;
                    gridScoreMap.put(score, grid);
                });
                threads.add(thread);
            }

            for (var thread : threads) {
                thread.start();
            }
            for (var thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            result = gridScoreMap.get(Collections.max(gridScoreMap.keySet()));

            while (gridScoreMap.size() > 1) {
                var minScore = Collections.min(gridScoreMap.keySet());
                gridScoreMap.remove(minScore);
            }

            StdOut.println("Iteration " + i + " done (" + i * 100 / ITERATIONS + "%)");
        }


        return result;
    }

    public static void main(String[] args) throws InterruptedException {

        var grid = generate(25);

        var gridDraftsman = new GridDraftsman(grid.getSquares().length);
        gridDraftsman.drawGrid(grid);

        System.out.println(grid);
    }
}
