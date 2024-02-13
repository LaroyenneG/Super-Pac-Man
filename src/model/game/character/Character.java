package model.game.character;

import model.game.Entity;
import model.game.food.Food;

import java.awt.*;

public abstract class Character extends Entity {

    private Heading heading;
    private boolean moving;
    private double speed;


    protected Character() {

    }


    public abstract boolean isHero();

    public abstract boolean eats(Character character);

    public abstract boolean eats(Food food);
}
