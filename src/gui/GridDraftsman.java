package gui;

import model.game.character.ghost.*;
import model.game.character.pac.person.PacMan;
import model.game.character.pac.person.PacPerson;
import model.game.grid.Grid;
import model.game.grid.square.Space;
import model.game.grid.square.Square;
import model.game.grid.square.Wall;
import model.game.grid.square.door.HauntedDoor;
import model.game.grid.square.door.PacDoor;
import stdlib.StdDraw;
import stdlib.StdRandom;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class GridDraftsman {
    public static final String APPLICATION_TITLE = "Super Pac-Man";
    private static final int HEIGHT = 900;
    private static final int WIDTH = 900;

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = Color.BLUE;
    private static final Color DOOR_COLOR = Color.GRAY;
    private static final Color GHOST_SCARED_COLOR = Color.BLUE;
    private static final Color SPACE_BORDERS_COLOR = Color.DARK_GRAY;

    private static final int PACMAN_SHAPE_POINTS = 50;
    private static final int GHOSTS_SHAPE_POINTS = 70;

    private final int gridSize;
    private final double squareHalfHeight;
    private final double squareHalfWidth;

    public GridDraftsman(int gridSize) {
        this.gridSize = gridSize;
        squareHalfHeight = 1.0 / gridSize / 2.0;
        squareHalfWidth = 1.0 / gridSize / 2.0;
        StdDraw.setTitle(APPLICATION_TITLE);
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setScale(0, 1);
        StdDraw.enableDoubleBuffering();
        clear();
        StdDraw.show();
    }

    public void clear() {
        StdDraw.clear(BACKGROUND_COLOR);
    }


    private double centerX(int x, double halfWidth) {
        return x * 1.0 / gridSize + halfWidth;
    }

    private double centerY(int y, double halfHeight) {
        return ((gridSize - y) * 1.0 / gridSize - halfHeight);
    }

    public void drawPacDoor(int x, int y, PacDoor pacDoor) {

        StdDraw.setPenColor(DOOR_COLOR);

        StdDraw.filledRectangle(centerX(x, squareHalfWidth), centerY(y, squareHalfHeight), squareHalfWidth, squareHalfHeight / 3.0);
    }

    public void drawHauntedDoor(int x, int y, HauntedDoor hauntedDoor) {

        StdDraw.setPenColor(DOOR_COLOR);

        StdDraw.filledRectangle(centerX(x, squareHalfWidth), centerY(y, squareHalfHeight), squareHalfWidth, squareHalfHeight / 3.0);
    }

    private static List<Point2D.Double> translatePoints(List<Point2D.Double> points, double tx, double ty) {

        var result = new ArrayList<Point2D.Double>();

        for (var point : points) {
            var x = point.x + tx;
            var y = point.y + ty;
            result.add(new Point2D.Double(x, y));
        }

        return result;
    }

    public void drawWall(int x, int y, Wall wall) {

        StdDraw.setPenColor(WALL_COLOR);

        StdDraw.filledRectangle(centerX(x, squareHalfWidth), centerY(y, squareHalfHeight), squareHalfWidth, squareHalfHeight);
    }

    private static List<Point2D.Double> rotatePoints(List<Point2D.Double> points, double angle) {

        var result = new ArrayList<Point2D.Double>();

        for (var point : points) {
            var x = point.x * Math.cos(angle) - point.y * Math.sin(angle);
            var y = point.x * Math.sin(angle) + point.y * Math.cos(angle);
            result.add(new Point2D.Double(x, y));
        }

        return result;
    }

    private static void drawFilledPolygon(List<Point2D.Double> points) {

        var x = new double[points.size()];
        var y = new double[points.size()];

        for (var i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }

        StdDraw.filledPolygon(x, y);
    }

    private static List<Point2D.Double> ghostFeetShape(double halfWidth, double halfHeight, boolean moving) {

        var result = new ArrayList<Point2D.Double>();

        result.add(new Point2D.Double(halfWidth, -halfHeight));
        result.add(new Point2D.Double(halfWidth, halfHeight));

        result.add(new Point2D.Double(-halfWidth, halfHeight));

        var angleMax = 8.0 * Math.PI;

        for (var angle = 0.0; angle <= angleMax; angle += (angleMax / GHOSTS_SHAPE_POINTS)) {
            var x = -halfWidth + (2.0 * halfWidth) * angle / angleMax;
            var y = -halfHeight + halfHeight / 4.0 * (moving ? Math.cos(angle) : Math.sin(angle));
            result.add(new Point2D.Double(x, y));
        }



        return result;
    }


    public void drawSpace(int x, int y, Space space) {

        StdDraw.setPenColor(SPACE_BORDERS_COLOR);

        StdDraw.rectangle(centerX(x, squareHalfWidth), centerY(y, squareHalfHeight), squareHalfWidth, squareHalfHeight);
    }

    public void drawGhost(Ghost ghost) {

        var color = (ghost.isScared()) ? GHOST_SCARED_COLOR : ghost.getColor();

        StdDraw.setPenColor(color);

        var position = ghost.getPosition();

        var size = 0.8;
        var halfWidth = squareHalfWidth * size;
        var halfHeight = squareHalfHeight * size;
        var rayon = Math.min(halfHeight, halfWidth);

        StdDraw.filledCircle(centerX(position.x, squareHalfWidth), centerY(position.y, squareHalfHeight), rayon);
        drawFilledPolygon(
                translatePoints(
                        ghostFeetShape(halfWidth, halfHeight / 2.0, ghost.isMoving()),
                        centerX(position.x, squareHalfWidth),
                        centerY(position.y, squareHalfHeight) - halfHeight / 2.0
                )
        );

        StdDraw.setPenColor(Color.WHITE);


        var whiteEyesSize = 1.0 / 4.0;
        var whiteEyesOffsetY = squareHalfHeight / 3.5;
        var whiteEyesOffsetX = squareHalfWidth / 4.0;

        StdDraw.filledCircle(centerX(position.x, squareHalfWidth) + whiteEyesOffsetX, centerY(position.y, squareHalfHeight) + whiteEyesOffsetY, rayon * whiteEyesSize);
        StdDraw.filledCircle(centerX(position.x, squareHalfWidth) - whiteEyesOffsetX, centerY(position.y, squareHalfHeight) + whiteEyesOffsetY, rayon * whiteEyesSize);

        StdDraw.setPenColor(Color.BLACK);

        var blackEyesSize = 1.0 / 10.0;
        var blackEyesOffsetY = whiteEyesOffsetY;
        var blackEyesOffsetLeftX = whiteEyesOffsetX;
        var blackEyesOffsetRightX = whiteEyesOffsetX;

        var heading = ghost.getHeading();
        switch (heading) {
            case UP:
                blackEyesOffsetY += rayon * blackEyesSize;
                break;
            case DOWN:
                blackEyesOffsetY -= rayon * blackEyesSize;
                break;
            case RIGHT:
                blackEyesOffsetLeftX += rayon * blackEyesSize;
                blackEyesOffsetRightX -= rayon * blackEyesSize;
                break;
            case LEFT:
                blackEyesOffsetLeftX -= rayon * blackEyesSize;
                blackEyesOffsetRightX += rayon * blackEyesSize;
                break;
        }

        StdDraw.filledCircle(centerX(position.x, squareHalfWidth) + blackEyesOffsetLeftX, centerY(position.y, squareHalfHeight) + blackEyesOffsetY, rayon * blackEyesSize);
        StdDraw.filledCircle(centerX(position.x, squareHalfWidth) - blackEyesOffsetRightX, centerY(position.y, squareHalfHeight) + blackEyesOffsetY, rayon * blackEyesSize);
    }

    private List<Point2D.Double> pacmanShape(double size, boolean mouthOpen) {

        var result = new ArrayList<Point2D.Double>();

        var rayon = Math.min(squareHalfWidth, squareHalfHeight) * size;

        var mouthAngle = (mouthOpen) ? Math.PI / 4.0 : 0.0;

        var startAngle = 0.0 + mouthAngle;
        var endAngle = 2.0 * Math.PI - mouthAngle;

        for (var angle = startAngle; angle <= endAngle; angle += (Math.PI / PACMAN_SHAPE_POINTS)) {
            if (angle == startAngle) {
                result.add(new Point2D.Double(0.0, 0.0));
            }

            var x = Math.cos(angle) * rayon;
            var y = Math.sin(angle) * rayon;

            result.add(new Point2D.Double(x, y));

            if (angle == endAngle) {
                result.add(new Point2D.Double(0.0, 0.0));
            }
        }

        return result;
    }

    public void drawPacPerson(PacPerson pacPerson) {

        StdDraw.setPenColor(Color.YELLOW);

        var position = pacPerson.getPosition();

        drawFilledPolygon(
                translatePoints(
                        rotatePoints(
                                pacmanShape(1.0, true), Math.PI / 2.0),
                        squareHalfWidth, squareHalfHeight
                )
        );
    }


    public void drawGrid(Grid grid) {

        var squares = grid.getSquares();

        assert squares.length > 0;
        assert squares.length == gridSize;
        assert squares[0].length == squares.length;

        clear();
        drawSquares(squares);
        StdDraw.show();
    }


    public void drawSquares(Square[][] squares) {

        for (var i = 0; i < squares.length; i++) {
            for (var j = 0; j < squares.length; j++) {
                var square = squares[i][j];
                if (square instanceof Wall) { // change me
                    drawWall(j, i, (Wall) square);
                } else if (square instanceof Space) {
                    drawSpace(j, i, (Space) square);
                } else if (square instanceof HauntedDoor) {
                    drawHauntedDoor(j, i, (HauntedDoor) square);
                } else if (square instanceof PacDoor) {
                    drawPacDoor(j, i, (PacDoor) square);
                }
            }
        }

        drawGhost(new Blinky());
    }
}
