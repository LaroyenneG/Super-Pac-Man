package ai;

import model.game.grid.square.Square;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Compute {

    private Compute() {

    }


    private static Set<Point> theoreticalNextPositions(Point position) {

        var result = new HashSet<Point>();

        result.add(new Point(position.x + 1, position.y));
        result.add(new Point(position.x - 1, position.y));
        result.add(new Point(position.x, position.y + 1));
        result.add(new Point(position.x, position.y - 1));

        return result;
    }

    public static List<Point> findWay(Square[][] squares, Point from, Point to, List<Point> travel) {

        assert squares.length >= 1;
        assert !squares[from.y][from.x].isImpassable();
        assert !squares[to.y][to.x].isImpassable();

        travel.add(from);

        if (from.equals(to)) return travel;

        if (travel.size() > squares.length * 4) return null;

        for (var nextPosition : theoreticalNextPositions(from)) {

            if (travel.contains(nextPosition)) {
                continue;
            }

            if (nextPosition.y >= 0 && nextPosition.y < squares.length
                    && nextPosition.x >= 0 && nextPosition.x < squares[0].length) {

                if (squares[nextPosition.y][nextPosition.x].isImpassable()) {
                    continue;
                }

                var subTravel = findWay(squares, nextPosition, to, new ArrayList<>(travel));
                if (subTravel != null) {
                    return subTravel;
                }
            }
        }

        return null;
    }


    public static List<Point> findWay(Square[][] squares, Point from, Point to) {
        return findWay(squares, from, to, new ArrayList<>());
    }
}
