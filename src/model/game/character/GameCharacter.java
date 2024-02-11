package model.game.character;

import model.game.food.Food;
import model.game.grid.Square;

import java.awt.*;

public abstract class GameCharacter extends Square {

    private double vitality;

    private Heading heading;

    private boolean moving;

    private boolean speed;

    private Color color;


    public abstract boolean isHero();

    public abstract boolean eats(GameCharacter character);

    public abstract boolean eats(Food food);
}
