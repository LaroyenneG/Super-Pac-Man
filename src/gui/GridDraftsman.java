package gui;

import model.game.character.Heading;
import model.game.character.ghost.Ghost;
import model.game.character.ghost.Inky;
import model.game.character.pac.person.PacMan;
import model.game.character.pac.person.PacPerson;
import model.game.food.PacGum;
import model.game.food.ability.Lightning;
import model.game.food.ability.Star;
import model.game.food.ability.SuperPacGum;
import model.game.food.ability.Trident;
import model.game.food.fruit.*;
import model.game.grid.Grid;
import model.game.grid.square.Space;
import model.game.grid.square.Square;
import model.game.grid.square.Wall;
import model.game.grid.square.door.HauntedDoor;
import model.game.grid.square.door.PacDoor;
import stdlib.StdDraw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridDraftsman {
    public static final String APPLICATION_TITLE = "Super Pac-Man";
    private static final int HEIGHT = 900;
    private static final int WIDTH = 900;
    private static final Color BRUN = new Color(158, 115, 51);
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = Color.BLUE;
    private static final Color DOOR_COLOR = BRUN;
    private static final Color GHOST_SCARED_COLOR = Color.BLUE;
    private static final Color SPACE_BORDERS_COLOR = Color.DARK_GRAY;
    private static final double SUPER_PAC_GUM_SIZE = 0.4;
    private static final double PAC_GUM_SIZE = 0.2;
    private static final double STAR_SIZE = 0.6;
    private static final double ORANGE_SIZE = 0.4;
    private static final double APPLE_SIZE = 0.4;
    private static final double TRIDENT_SIZE = 0.5;
    private static final double LIGHTING_SIZE = 0.4;
    private static final double PEAR_SIZE = 0.4;
    private static final double CHERRY_SIZE = 0.3;
    private static final int PACMAN_SHAPE_POINTS = 50;
    private static final int GHOSTS_SHAPE_POINTS = 70;

    private static final Map<Heading, Double> HEADING_ANGLE_MAP = Map.of(Heading.UP, Math.PI / 2.0, Heading.DOWN, -Math.PI / 2.0, Heading.RIGHT, 0.0, Heading.LEFT, Math.PI);


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
        StdDraw.setPenRadius(1.0 / Math.max(WIDTH, HEIGHT));
        StdDraw.enableDoubleBuffering();
        clear();
        StdDraw.show();
    }

    public void clear() {
        StdDraw.clear(BACKGROUND_COLOR);
    }

    private double centerX(int x) {
        return centerX(x, squareHalfWidth);
    }

    private double centerX(int x, double halfWidth) {
        return x * 1.0 / gridSize + halfWidth;
    }

    private double centerY(int y) {
        return centerY(y, squareHalfHeight);
    }

    private double centerY(int y, double halfHeight) {
        return ((gridSize - y) * 1.0 / gridSize - halfHeight);
    }

    public void drawPacDoor(int x, int y, PacDoor pacDoor) {

        StdDraw.setPenColor(DOOR_COLOR);

        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight / 3.0);
    }

    public void drawHauntedDoor(int x, int y, HauntedDoor hauntedDoor) {

        StdDraw.setPenColor(DOOR_COLOR);

        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight / 3.0);
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

        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight);
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

    private static void drawPolygon(List<Point2D.Double> points) {
        drawPolygon(points, true);
    }

    private static void drawPolygon(List<Point2D.Double> points, boolean filled) {

        var x = new double[points.size()];
        var y = new double[points.size()];

        for (var i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }

        if (filled) {
            StdDraw.filledPolygon(x, y);
        } else {
            StdDraw.polygon(x, y);
        }
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
        StdDraw.rectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight);
    }

    public void drawGhost(Ghost ghost) {

        var scared = ghost.isScared();

        var color = (scared) ? GHOST_SCARED_COLOR : ghost.getColor();
        var heading = ghost.getHeading();

        StdDraw.setPenColor(color);

        var position = ghost.getPosition();

        var size = 0.8;
        var halfWidth = squareHalfWidth * size;
        var halfHeight = squareHalfHeight * size;
        var rayon = Math.min(halfHeight, halfWidth);

        StdDraw.filledCircle(centerX(position.x) + movingTranslationX(heading),
                centerY(position.y) + movingTranslationY(heading), rayon);
        drawPolygon(
                translatePoints(
                        ghostFeetShape(halfWidth, halfHeight / 2.0, ghost.isMoving()),
                        centerX(position.x) + movingTranslationX(heading),
                        centerY(position.y) + movingTranslationY(heading) - halfHeight / 2.0
                )
        );


        StdDraw.setPenColor(Color.WHITE);

        var whiteEyesSize = 1.0 / 4.0;
        var whiteEyesOffsetY = squareHalfHeight / 3.5;
        var whiteEyesOffsetX = squareHalfWidth / 4.0;

        StdDraw.filledCircle(centerX(position.x) + movingTranslationX(heading) + whiteEyesOffsetX, centerY(position.y) + movingTranslationY(heading) + whiteEyesOffsetY, rayon * whiteEyesSize);
        StdDraw.filledCircle(centerX(position.x) + movingTranslationX(heading) - whiteEyesOffsetX, centerY(position.y) + movingTranslationY(heading) + whiteEyesOffsetY, rayon * whiteEyesSize);

        if (!scared) {
            StdDraw.setPenColor(Color.BLACK);

            var blackEyesSize = 1.0 / 10.0;
            var blackEyesOffsetY = whiteEyesOffsetY;
            var blackEyesOffsetLeftX = whiteEyesOffsetX;
            var blackEyesOffsetRightX = whiteEyesOffsetX;

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

            StdDraw.filledCircle(centerX(position.x) + movingTranslationX(heading) + blackEyesOffsetLeftX, centerY(position.y) + movingTranslationY(heading) + blackEyesOffsetY, rayon * blackEyesSize);
            StdDraw.filledCircle(centerX(position.x) + movingTranslationX(heading) - blackEyesOffsetRightX, centerY(position.y) + movingTranslationY(heading) + blackEyesOffsetY, rayon * blackEyesSize);
        }
    }

    private List<Point2D.Double> pacmanShape(double size, double mouthAngle) {

        var result = new ArrayList<Point2D.Double>();

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * size;

        var startAngle = 0.0 + mouthAngle;
        var endAngle = 2.0 * Math.PI - mouthAngle;

        for (var angle = startAngle; angle <= endAngle; angle += (Math.PI / PACMAN_SHAPE_POINTS)) {
            if (angle == startAngle) {
                result.add(new Point2D.Double(0.0, 0.0));
            }

            var x = Math.cos(angle) * radius;
            var y = Math.sin(angle) * radius;
            result.add(new Point2D.Double(x, y));

            if (angle == endAngle) {
                result.add(new Point2D.Double(0.0, 0.0));
            }
        }

        return result;
    }

    public double movingTranslationX(Heading heading) {
        return switch (heading) {
            case RIGHT -> squareHalfWidth;
            case LEFT -> -squareHalfWidth;
            default -> 0.0;
        };
    }

    public double movingTranslationY(Heading heading) {
        return switch (heading) {
            case UP -> squareHalfHeight;
            case DOWN -> -squareHalfHeight;
            default -> 0.0;
        };
    }

    public void drawCherry(Cherry cherry) {

        var position = cherry.getPosition();
        var radius = Math.min(squareHalfWidth, squareHalfHeight) * CHERRY_SIZE / 2.0;
        var circlesMargin = radius * 1.5;

        StdDraw.setPenColor(Color.GREEN);

        StdDraw.filledEllipse(centerX(position.x), centerY(position.y) + circlesMargin, radius, radius / 2.0);
        StdDraw.line(centerX(position.x) - circlesMargin, centerY(position.y) - circlesMargin, centerX(position.x), centerY(position.y) + circlesMargin);
        StdDraw.line(centerX(position.x) + circlesMargin, centerY(position.y) - circlesMargin, centerX(position.x), centerY(position.y) + circlesMargin);

        StdDraw.setPenColor(Color.RED);

        StdDraw.filledCircle(centerX(position.x) - circlesMargin, centerY(position.y) - circlesMargin, radius);
        StdDraw.filledCircle(centerX(position.x) + circlesMargin, centerY(position.y) - circlesMargin, radius);
    }

    public void drawPacPerson(PacPerson pacPerson, Color color) {

        StdDraw.setPenColor(color);

        var heading = pacPerson.getHeading();
        var position = pacPerson.getPosition();
        var mouthOpen = !pacPerson.isMoving();
        var mouthAngle = (mouthOpen) ? Math.PI / 4.0 : 0.0;

        drawPolygon(
                translatePoints(
                        rotatePoints(
                                pacmanShape(0.5, mouthAngle), HEADING_ANGLE_MAP.get(heading)),
                        centerX(position.x) + movingTranslationX(heading),
                        centerY(position.y) + movingTranslationY(heading)
                ), true
        );
    }


    public void drawFruit(Fruit fruit) {

    }


    public void drawBanana(Banana banana) {
        StdDraw.setPenColor(Color.YELLOW);
    }

    public void drawOrange(Orange orange) {
        StdDraw.setPenColor(Color.ORANGE);

        var position = orange.getPosition();
        var radius = Math.min(squareHalfWidth, squareHalfHeight) * ORANGE_SIZE;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y), radius);

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledEllipse(centerX(position.x), centerY(position.y) + radius, radius * 0.8, radius / 4.0);
    }


    public void drawApple(Apple apple) {
        StdDraw.setPenColor(Color.RED);

        var position = apple.getPosition();
        var radius = Math.min(squareHalfWidth, squareHalfHeight) * APPLE_SIZE;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y), radius);

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledEllipse(centerX(position.x) - radius / 2.0, centerY(position.y) + radius, radius / 2.0, radius / 4.0);
    }


    public void drawPear(Pear pear) {
        StdDraw.setPenColor(Color.GREEN);

        var position = pear.getPosition();
        var radius = Math.min(squareHalfWidth, squareHalfHeight) * PEAR_SIZE;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y) - radius / 2.0, radius);
        StdDraw.filledCircle(centerX(position.x), centerY(position.y) + radius / 4.0, radius / 1.5);

        StdDraw.setPenColor(BRUN);
        StdDraw.filledRectangle(centerX(position.x), centerY(position.y) + radius, radius / 5.0, radius / 4.0);
    }

    public void drawPacGum(PacGum pacGum, double size) {

        StdDraw.setPenColor(Color.ORANGE);

        var position = pacGum.getPosition();

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * size;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y), radius);
    }


    public void drawPacGum(PacGum pacGum) {
        drawPacGum(pacGum, PAC_GUM_SIZE);
    }

    public void drawSuperPacGum(SuperPacGum supePacGum) {
        drawPacGum(supePacGum, SUPER_PAC_GUM_SIZE);
    }


    public void drawStar(Star star) {

        StdDraw.setPenColor(Color.YELLOW);

        var points = new ArrayList<Point2D.Double>();

        var position = star.getPosition();

        var alternate = false;
        for (var angle = 0.0; angle <= 2.0 * Math.PI; angle += Math.PI / 5.0) {
            var rayon = Math.min(squareHalfWidth, squareHalfHeight) * (alternate ? STAR_SIZE : STAR_SIZE / 2.0);
            var x = Math.cos(angle) * rayon;
            var y = Math.sin(angle) * rayon;
            points.add(new Point2D.Double(x, y));
            alternate = !alternate;
        }

        drawPolygon(translatePoints(points, centerX(position.x), centerY(position.y)));
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


        drawStar(new Star());
        drawSuperPacGum(new SuperPacGum());
        drawPacGum(new PacGum());
        drawGhost(new Inky());
        drawPacPerson(new PacMan(), Color.RED);
        drawTrident(new Trident());
        drawLightning(new Lightning());
        drawOrange(new Orange());
        drawApple(new Apple());
        drawPear(new Pear());
        drawCherry(new Cherry());
    }

    private void drawLightning(Lightning lightning) {

        StdDraw.setPenColor(Color.YELLOW);

        var position = lightning.getPosition();

        var width = Math.min(squareHalfWidth, squareHalfHeight) * LIGHTING_SIZE;
        var blockSize = width / 5.0;

        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 3.0, centerY(position.y) + width - blockSize, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 3.0, centerY(position.y) + width - blockSize * 2.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 3.0, centerY(position.y) + width - blockSize * 3.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 3.0, centerY(position.y) + width - blockSize * 4.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 3.0, centerY(position.y) + width - blockSize * 5.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 3.0, centerY(position.y) + width - blockSize * 6.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 4.0, centerY(position.y) + width - blockSize * 6.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 5.0, centerY(position.y) + width - blockSize * 6.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 5.0, centerY(position.y) + width - blockSize * 5.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 6.0, centerY(position.y) + width - blockSize * 6.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 6.0, centerY(position.y) + width - blockSize * 7.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 6.0, centerY(position.y) + width - blockSize * 8.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 6.0, centerY(position.y) + width - blockSize * 9.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 6.0, centerY(position.y) + width - blockSize * 10.0, blockSize, blockSize);
        StdDraw.filledRectangle(centerX(position.x) - width + blockSize * 6.0, centerY(position.y) + width - blockSize * 11.0, blockSize, blockSize);
    }


    private void drawTrident(Trident trident) {

        StdDraw.setPenColor(Color.RED);

        var position = trident.getPosition();

        var halfHeight = squareHalfHeight * TRIDENT_SIZE;
        var halfWidth = squareHalfWidth * TRIDENT_SIZE / 2.0;
        var tridentWidth = squareHalfWidth * TRIDENT_SIZE / 10.0;
        var tridentHeight = squareHalfHeight * TRIDENT_SIZE / 10.0;

        StdDraw.filledRectangle(centerX(position.x), centerY(position.y), tridentWidth, halfHeight);
        StdDraw.filledRectangle(centerX(position.x), centerY(position.y) + tridentHeight, halfWidth, tridentHeight);
        StdDraw.filledRectangle(centerX(position.x) - halfWidth, centerY(position.y) + halfHeight / 2.0, tridentWidth, halfHeight / 2.0);
        StdDraw.filledRectangle(centerX(position.x) + halfWidth, centerY(position.y) + halfHeight / 2.0, tridentWidth, halfHeight / 2.0);
    }
}
