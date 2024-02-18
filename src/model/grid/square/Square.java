package model.grid.square;

import model.entity.food.Food;
import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

public abstract class Square {

    public abstract boolean isImpassable();

    public abstract boolean isFree();

    public abstract boolean accept(PacPerson pacPerson);

    public abstract boolean accept(Ghost ghost);

    public abstract boolean accept(Food food);
}
