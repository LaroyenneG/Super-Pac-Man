package model.game.character;

import model.game.food.Food;
import model.game.grid.square.Square;

import java.awt.*;

public abstract class GameCharacter extends Square {

    private Heading heading;

    private boolean moving;

    private boolean speed;

    private Color color;


    public abstract boolean isHero();

    public abstract boolean eats(GameCharacter character);

    public abstract boolean eats(Food food);

    @Override
    public boolean isImpassable() {
        return false;
    }
}
