package gui;

import model.Game;
import model.Heading;
import model.Player;
import model.entity.Entity;
import model.entity.food.PacGum;
import model.entity.food.ability.Lightning;
import model.entity.food.ability.Star;
import model.entity.food.ability.SuperPacGum;
import model.entity.food.ability.Trident;
import model.entity.food.fruit.*;
import model.entity.individual.ghost.*;
import model.entity.individual.pac.person.PacDevil;
import model.entity.individual.pac.person.PacMan;
import model.entity.individual.pac.person.PacPerson;
import model.entity.individual.pac.person.SuperPac;
import model.grid.Grid;
import model.grid.square.Area;
import model.grid.square.Space;
import model.grid.square.Square;
import model.grid.square.Wall;
import model.grid.square.door.HauntedDoor;
import model.grid.square.door.PacDoor;
import stdlib.StdDraw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameDraftsman {
    public static final String APPLICATION_TITLE = "Super Pac-Man";
    private static final int HEIGHT = 900;
    private static final int WIDTH = 900;
    private static final Color BRUN = new Color(158, 115, 51);
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = Color.BLUE;
    private static final Color DOOR_COLOR = BRUN;
    private static final Color DARK_ORANGE = new Color(255, 140, 0);
    private static final Color GHOST_SCARED_COLOR = Color.BLUE;
    private static final Color WALL_BORDERS_COLOR = new Color(0, 0, 128);
    private static final double SUPER_PAC_GUM_SIZE = 0.25;
    private static final double PAC_GUM_SIZE = 0.15;
    private static final double STAR_SIZE = 0.6;
    private static final double ORANGE_SIZE = 0.4;
    private static final double BANANA_SIZE = 0.6;
    private static final double APPLE_SIZE = 0.4;
    private static final double TRIDENT_SIZE = 0.5;
    private static final double LIGHTING_SIZE = 0.4;
    private static final double PEAR_SIZE = 0.4;
    private static final double MELON_SIZE = 0.4;
    private static final double CHERRY_SIZE = 0.4;
    private static final double STRAWBERRY_SIZE = 0.6;
    private static final double PEACH_SIZE = 0.3;
    private static final int PACMAN_SHAPE_POINTS = 50;
    private static final int GHOSTS_SHAPE_POINTS = 70;

    private static final Map<Heading, Double> HEADING_ANGLE_MAP = Map.of(Heading.UP, Math.PI / 2.0, Heading.DOWN, -Math.PI / 2.0, Heading.RIGHT, 0.0, Heading.LEFT, Math.PI);


    private static final String DRAW_METHOD_NAME = "draw";

    private final int gridSize;
    private final double squareHalfHeight;
    private final double squareHalfWidth;

    public GameDraftsman(int gridSize) {
        this.gridSize = gridSize;
        squareHalfHeight = 1.0 / gridSize / 2.0;
        squareHalfWidth = 1.0 / gridSize / 2.0;
    }

    public void init() {
        StdDraw.setTitle(APPLICATION_TITLE);
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setScale(0, 1);
        StdDraw.setPenRadius(gridSize / 10.0 / Math.max(WIDTH, HEIGHT));
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


    private static List<Point2D.Double> translatePoints(List<Point2D.Double> points, double tx, double ty) {

        var result = new ArrayList<Point2D.Double>();

        for (var point : points) {
            var x = point.x + tx;
            var y = point.y + ty;
            result.add(new Point2D.Double(x, y));
        }

        return result;
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

    private List<Point2D.Double> arcPolygon(double size, double startAngle, double endAngle) {

        var result = new ArrayList<Point2D.Double>();

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * size;

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

    private List<Point2D.Double> arcPolygon(double size, double mouthAngle) {

        var startAngle = 0.0 + mouthAngle;
        var endAngle = 2.0 * Math.PI - mouthAngle;

        return arcPolygon(size, startAngle, endAngle);
    }

    private double movingTranslationX(Heading heading) {
        return switch (heading) {
            case RIGHT -> squareHalfWidth;
            case LEFT -> -squareHalfWidth;
            case null, default -> 0.0;
        };
    }

    private double movingTranslationY(Heading heading) {
        return switch (heading) {
            case UP -> squareHalfHeight;
            case DOWN -> -squareHalfHeight;
            case null, default -> 0.0;
        };
    }

    
    private double playerX(int id) {
        return switch (id) {
            case 0 -> 0.90;
            case 1 -> 0.1;
            default -> 0;
        };
    }

    private double playerY(int id) {
        return switch (id) {
            case 0 -> 0.015;
            case 1 -> 0.985;
            default -> 0;
        };
    }



    private void draw(Player player, int id) {
        assert id >= 0 && id <= 2;

        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.filledRectangle(playerX(id), playerY(id), 0.1, 0.013);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 12));
        StdDraw.setPenColor(player.getColor());
        StdDraw.text(playerX(id), playerY(id), String.format("%s : %d", player.getName(), player.getScore()));
    }


    public void draw(Game game) {

        clear();

        var grid = game.getGrid();
        draw(grid);

        var players = game.getPlayers();
        for (var i = 0; i < players.length; i++) {
            var player = players[i];
            draw(player, i);
        }

        StdDraw.show();
    }

    private void draw(Grid grid) {

        var squares = grid.getSquares();

        assert squares.length == gridSize;
        assert squares.length > 0;
        assert squares[0].length == squares.length;

        draw(squares);

        var foods = grid.getFoods();
        for (var food : foods) {
            draw(food);
        }

        var ghosts = grid.getGhosts();
        for (var ghost : ghosts) {
            draw(ghost);
        }

        var pacPeople = grid.getPacPeople();
        for (var pacPerson : pacPeople) {
            draw(pacPerson);
        }
    }

    private void draw(Entity entity) {
        var entityClass = entity.getClass();
        try {
            var draw = GameDraftsman.class.getDeclaredMethod(DRAW_METHOD_NAME, entityClass);
            draw.invoke(this, entity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void draw(Square[][] squares) {
        for (var i = 0; i < squares.length; i++) {
            for (var j = 0; j < squares.length; j++) {
                var square = squares[i][j];
                var squareClass = square.getClass();
                try {
                    var draw = GameDraftsman.class.getDeclaredMethod(DRAW_METHOD_NAME, int.class, int.class, squareClass);
                    draw.invoke(this, j, i, square);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*******************************************************************************************************************
     * Characters
     *******************************************************************************************************************/

    private void draw(Blinky blinky) {
        draw((Ghost) blinky);
    }

    private void draw(Clyde clyde) {
        draw((Ghost) clyde);
    }

    private void draw(Inky inky) {
        draw((Ghost) inky);
    }

    private void draw(Pinky pinky) {
        draw((Ghost) pinky);
    }

    private void draw(Ghost ghost) {

        var position = ghost.getPosition();

        var scared = ghost.isScared();
        var alive = ghost.isAlive();

        var color = (scared) ? GHOST_SCARED_COLOR : ghost.getColor();
        var heading = ghost.getHeading();

        StdDraw.setPenColor(alive ? color : Color.GRAY);

        var size = (alive) ? 0.8 : 0.3;
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

        if (!scared && alive) {
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
                case null:
                default:
                    break;
            }

            StdDraw.filledCircle(centerX(position.x) + movingTranslationX(heading) + blackEyesOffsetLeftX, centerY(position.y) + movingTranslationY(heading) + blackEyesOffsetY, rayon * blackEyesSize);
            StdDraw.filledCircle(centerX(position.x) + movingTranslationX(heading) - blackEyesOffsetRightX, centerY(position.y) + movingTranslationY(heading) + blackEyesOffsetY, rayon * blackEyesSize);
        }
    }

    private void draw(PacPerson pacPerson, Color color, double size, boolean filled) {

        var position = pacPerson.getPosition();
        var mouthOpen = !pacPerson.isMoving();
        var mouthAngle = (mouthOpen) ? Math.PI / 4.0 : 0.0;
        var heading = pacPerson.getHeading();

        StdDraw.setPenColor(color);

        drawPolygon(
                translatePoints(
                        rotatePoints(
                                arcPolygon(size, mouthAngle), (heading != null) ? HEADING_ANGLE_MAP.get(heading) : 0.0),
                        centerX(position.x) + movingTranslationX(heading),
                        centerY(position.y) + movingTranslationY(heading)
                ), filled
        );
    }

    private void draw(SuperPac superPac) {
        draw(superPac, (superPac.isAlive()) ? superPac.getColor() : Color.GRAY, superPac.getWeight(), false);
    }

    private void draw(PacMan pacMan) {
        draw(pacMan, (pacMan.isAlive()) ? pacMan.getColor() : Color.GRAY, pacMan.getWeight(), true);
    }

    private void draw(PacDevil pacDevil) {
        draw(pacDevil, Color.RED, pacDevil.getWeight(), true);
        draw(pacDevil, (pacDevil.isAlive()) ? pacDevil.getColor() : Color.GRAY, pacDevil.getWeight(), false);
    }


    /*******************************************************************************************************************
     * Fruits
     *******************************************************************************************************************/

    private void draw(Melon melon) {

        var position = melon.getPosition();

        StdDraw.setPenColor(Color.GREEN);

        drawPolygon(translatePoints(rotatePoints(arcPolygon(MELON_SIZE, Math.PI / 2.0), Math.PI / 4.0), centerX(position.x) + squareHalfWidth * MELON_SIZE / 2.0, centerY(position.y) + squareHalfHeight * MELON_SIZE / 4.0));

        StdDraw.setPenColor(Color.RED);

        drawPolygon(translatePoints(rotatePoints(arcPolygon(MELON_SIZE * 0.9, Math.PI / 2.0), Math.PI / 4.0), centerX(position.x) + squareHalfWidth * MELON_SIZE / 2.0, centerY(position.y) + squareHalfHeight * MELON_SIZE / 4.0));
    }


    private void draw(Strawberry strawberry) {

        var position = strawberry.getPosition();

        StdDraw.setPenColor(Color.RED);


        drawPolygon(translatePoints(rotatePoints(arcPolygon(STRAWBERRY_SIZE, 5.0 * Math.PI / 6.0, 7.0 * Math.PI / 6.0), -Math.PI / 2.0), centerX(position.x), centerY(position.y) - squareHalfHeight * STRAWBERRY_SIZE / 2.0));

        StdDraw.setPenColor(Color.GREEN);

        StdDraw.filledEllipse(centerX(position.x), centerY(position.y) + squareHalfHeight * STRAWBERRY_SIZE / 2.0 + squareHalfHeight * STRAWBERRY_SIZE / 8.0 / 2.0, squareHalfWidth * STRAWBERRY_SIZE / 2.5, squareHalfHeight * STRAWBERRY_SIZE / 8.0);
    }

    private void draw(Peach peach) {

        var position = peach.getPosition();

        StdDraw.setPenColor(DARK_ORANGE);

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * PEACH_SIZE;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y), radius);

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledEllipse(centerX(position.x) - radius / 2.0, centerY(position.y) + radius, radius * 0.6, radius / 4.0);
    }

    private void draw(Cherry cherry) {

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

    private void draw(Banana banana) {

        var position = banana.getPosition();

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledEllipse(centerX(position.x) + squareHalfWidth * BANANA_SIZE / 4.0, centerY(position.y), squareHalfWidth * BANANA_SIZE / 2.0, squareHalfHeight * BANANA_SIZE / 5.0);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.filledEllipse(centerX(position.x) - squareHalfWidth * BANANA_SIZE / 4.0, centerY(position.y), squareHalfWidth * BANANA_SIZE / 2.0, squareHalfHeight * BANANA_SIZE / 4.5);
    }

    private void draw(Orange orange) {

        var position = orange.getPosition();

        StdDraw.setPenColor(DARK_ORANGE);

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * ORANGE_SIZE;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y), radius);

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledEllipse(centerX(position.x), centerY(position.y) + radius, radius * 0.8, radius / 4.0);
    }


    private void draw(Apple apple) {

        var position = apple.getPosition();

        StdDraw.setPenColor(Color.RED);

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * APPLE_SIZE;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y), radius);

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.filledEllipse(centerX(position.x) - radius / 2.0, centerY(position.y) + radius, radius / 2.0, radius / 4.0);
    }


    private void draw(Pear pear) {

        var position = pear.getPosition();

        StdDraw.setPenColor(Color.GREEN);

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * PEAR_SIZE;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y) - radius / 2.0, radius);
        StdDraw.filledCircle(centerX(position.x), centerY(position.y) + radius / 4.0, radius / 1.5);

        StdDraw.setPenColor(BRUN);
        StdDraw.filledRectangle(centerX(position.x), centerY(position.y) + radius, radius / 5.0, radius / 4.0);
    }


    /*******************************************************************************************************************
     * Abilities
     ******************************************************************************************************************/

    private void draw(Trident trident) {

        var position = trident.getPosition();

        StdDraw.setPenColor(Color.RED);

        var halfHeight = squareHalfHeight * TRIDENT_SIZE;
        var halfWidth = squareHalfWidth * TRIDENT_SIZE / 2.0;
        var tridentWidth = squareHalfWidth * TRIDENT_SIZE / 10.0;
        var tridentHeight = squareHalfHeight * TRIDENT_SIZE / 10.0;

        StdDraw.filledRectangle(centerX(position.x), centerY(position.y), tridentWidth, halfHeight);
        StdDraw.filledRectangle(centerX(position.x), centerY(position.y) + tridentHeight, halfWidth, tridentHeight);
        StdDraw.filledRectangle(centerX(position.x) - halfWidth, centerY(position.y) + halfHeight / 2.0, tridentWidth, halfHeight / 2.0);
        StdDraw.filledRectangle(centerX(position.x) + halfWidth, centerY(position.y) + halfHeight / 2.0, tridentWidth, halfHeight / 2.0);
    }

    private void draw(Lightning lightning) {

        var position = lightning.getPosition();

        StdDraw.setPenColor(Color.YELLOW);

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

    private void draw(Star star) {

        var position = star.getPosition();

        StdDraw.setPenColor(Color.YELLOW);

        var points = new ArrayList<Point2D.Double>();

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

    private void draw(PacGum pacGum) {
        draw(pacGum, PAC_GUM_SIZE);
    }

    private void draw(SuperPacGum supePacGum) {
        draw(supePacGum, SUPER_PAC_GUM_SIZE);
    }

    private void draw(PacGum pacGum, double size) {

        var position = pacGum.getPosition();

        StdDraw.setPenColor(Color.ORANGE);

        var radius = Math.min(squareHalfWidth, squareHalfHeight) * size;

        StdDraw.filledCircle(centerX(position.x), centerY(position.y), radius);
    }

    /*******************************************************************************************************************
     * Squares
     *******************************************************************************************************************/

    private void draw(int x, int y, PacDoor pacDoor) {

        StdDraw.setPenColor(DOOR_COLOR);
        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight / 3.0);

        StdDraw.setPenColor(Color.WHITE);
        drawPolygon(
                translatePoints(
                        rotatePoints(
                                arcPolygon(0.2, Math.PI / 4.0), 0.0),
                        centerX(x),
                        centerY(y)
                ), true
        );
    }

    private void draw(int x, int y, HauntedDoor hauntedDoor) {

        StdDraw.setPenColor(DOOR_COLOR);

        var height = squareHalfHeight / 3.0;

        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, height);

        StdDraw.setPenColor(Color.WHITE);

        var radius = height / 2.0;

        StdDraw.filledCircle(centerX(x), centerY(y), radius);
        drawPolygon(
                translatePoints(
                        ghostFeetShape(radius, radius / 2.0, false),
                        centerX(x),
                        centerY(y) - radius / 2.0
                )
        );
    }

    private void draw(int x, int y, Wall wall) {

        StdDraw.setPenColor(WALL_COLOR);

        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight);

        StdDraw.setPenColor(WALL_BORDERS_COLOR);
        StdDraw.rectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight);
    }

    private void draw(int x, int y, Space space) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight);
    }

    private void draw(int x, int y, Area area) {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight);
        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.rectangle(centerX(x), centerY(y), squareHalfWidth, squareHalfHeight);
    }
}
