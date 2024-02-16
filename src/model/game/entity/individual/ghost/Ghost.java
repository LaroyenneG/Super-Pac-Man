package model.game.entity.individual.ghost;

import model.game.entity.food.Food;
import model.game.entity.individual.Individual;
import model.game.entity.individual.pac.person.PacPerson;

import java.awt.*;

public abstract class Ghost extends Individual {

    public static final double SPEED = 0.5;

    private boolean scared;


    public Ghost(Color color) {
        super(color, SPEED);
        scared = false;
    }


    public void scareOff() {
        scared = true;
    }

    public void reassure() {
        scared = false;
    }

    @Override
    public final boolean isHero() {
        return true;
    }

    public boolean isScared() {
        return scared;
    }

    @Override
    public final boolean eats(Individual individual) {

        if(!individual.isHero()) return false;

        var pacPerson = (PacPerson) individual;
        /*
        if(scared && pacPerson.) {

        }
         */

        return false;
    }

    @Override
    public final boolean eats(Food food) {
        return false;
    }

    @Override
    public String toString() {
        return "^";
    }
}
