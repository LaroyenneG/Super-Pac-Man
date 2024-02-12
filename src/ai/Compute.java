package ai;

import model.game.grid.square.Square;

import java.awt.*;
import java.util.List;
import java.util.*;

public final class Compute {

    private Compute() {

    }


    private static Set<Point> theoreticalNextPositions(Point position) {

        var result = new HashSet<Point>();

        for (var i = -1; i <= 1; i++) {
            for (var j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    result.add(new Point(position.x + j, position.y + i));
                }
            }
        }

        return result;
    }

    public static List<Point> findWay(Square[][] squares, Point from, Point to, List<Point> travel) {


        if (from.equals(to)) return travel;

        var nextPositions = theoreticalNextPositions(from);
        for (var nextPosition : nextPositions) {
            if (travel.contains(nextPosition)) {
                continue;
            }

            if (nextPosition.y >= 0 && nextPosition.y < squares.length
                    && nextPosition.x >= 0 && nextPosition.x < squares[0].length) {

                var square = squares[nextPosition.y][nextPosition.x];
                if (square.isImpassable()) {
                    continue;
                }

                var newTravel = new ArrayList<Point>();
                newTravel.addAll(travel);
                newTravel.add(from);

                var result = findWay(squares, nextPosition, to, newTravel);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }


    public static List<Point> findWay(Square[][] squares, Point from, Point to) {

        return findWay(squares, from, to, new ArrayList<>());
    }
}
