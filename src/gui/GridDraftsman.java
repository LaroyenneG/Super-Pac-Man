package gui;

import model.game.character.ghost.Blinky;
import model.game.character.ghost.Ghost;
import model.game.character.ghost.Pinky;
import model.game.character.pac.person.PacPerson;
import model.game.grid.Grid;
import model.game.grid.square.Square;
import model.game.grid.square.Wall;
import model.game.grid.square.door.HauntedDoor;
import model.game.grid.square.door.PacDoor;
import stdlib.StdDraw;

import java.awt.*;

public class GridDraftsman {
    public static final String APPLICATION_TITLE = "Super Pac-Man";
    private static final int HEIGHT = 900;
    private static final int WIDTH = 900;

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = Color.BLUE;
    private static final Color DOOR_COLOR = Color.GRAY;
    private static final Color GHOST_SCARED_COLOR = Color.BLUE;

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


    public void drawWall(int x, int y, Wall wall) {

        StdDraw.setPenColor(WALL_COLOR);

        StdDraw.filledRectangle(centerX(x, squareHalfWidth), centerY(y, squareHalfHeight), squareHalfWidth, squareHalfHeight);
    }

    public void drawGhost(Ghost ghost) {

        var color = (ghost.isScared()) ? GHOST_SCARED_COLOR : ghost.getColor();

        StdDraw.setPenColor(color);

        var position = ghost.getPosition();

        StdDraw.filledCircle(centerX(position.x, squareHalfWidth), centerY(position.y, squareHalfHeight), Math.min(squareHalfWidth, squareHalfHeight));
    }


    private static void pacmanShape(int x, int y, int size) {

    }

    public void drawPacPerson(PacPerson pacPerson) {

        StdDraw.setPenColor(Color.YELLOW);

        var position = pacPerson.getPosition();

        //StdDraw.filledPolygon();
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
