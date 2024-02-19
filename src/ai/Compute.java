package ai;

import model.Heading;
import model.entity.individual.ghost.Ghost;
import model.grid.Grid;
import stdlib.StdRandom;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public final class Compute {

    private Compute() {

    }

    private static Point nextPosition(Point position, Heading heading) {
        return switch (heading) {
            case UP -> new Point(position.x, position.y - 1);
            case DOWN -> new Point(position.x, position.y + 1);
            case RIGHT -> new Point(position.x + 1, position.y);
            case LEFT -> new Point(position.x - 1, position.y);
        };
    }

    public static Heading ghostHeading(Ghost ghost, Grid grid) {

        var headings = Heading.values();
        var result = headings[StdRandom.uniformInt(headings.length)];

        var position = ghost.getPosition();
        var pacPeople = grid.getPacPeople();
        var others = new HashSet<>(grid.getGhosts());
        others.remove(ghost);
        var ghostsPosition = new HashSet<Point>();
        for (var other : others) {
            ghostsPosition.add(other.getPosition());
        }

        var headingCostMap = new HashMap<Heading, Integer>();

        for (var heading : headings) {

            var authorizedPosition = position;
            var cost = 0;
            var found = false;

            while (grid.accept(ghost, authorizedPosition) && !found) {
                for (var pacPerson : pacPeople) {
                    if (pacPerson.isGiant()) {
                        continue;
                    }

                    var targetPosition = pacPerson.getPosition();
                    if (Objects.equals(targetPosition, authorizedPosition)) {
                        headingCostMap.put(heading, cost);
                        found = true;
                        break;
                    }
                }

                authorizedPosition = nextPosition(authorizedPosition, heading);
                cost++;
            }
        }

        if (!headingCostMap.isEmpty()) {
            var min = Collections.min(headingCostMap.values());
            for (var key : headingCostMap.keySet()) {
                if (headingCostMap.get(key) <= min) {
                    result = key;
                    break;
                }
            }
        } else {
            var heading = ghost.getHeading();
            if (heading != null) {
                var newPosition = nextPosition(position, ghost.getHeading());
                if (grid.accept(ghost, newPosition) &&
                        !ghostsPosition.contains(newPosition) &&
                        StdRandom.bernoulli(0.9)) {
                    result = heading;
                }
            }
        }

        return result;
    }
}
