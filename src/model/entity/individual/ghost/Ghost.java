package model.entity.individual.ghost;

import model.entity.food.Food;
import model.entity.individual.Individual;
import model.entity.individual.pac.person.PacPerson;

import java.awt.*;

public abstract class Ghost extends Individual {

    public static final double SPEED = 30.0;

    private boolean scared;

    public Ghost(Color color) {
        super(color);
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
    public final boolean eats(PacPerson pacPerson) {

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
    public boolean eats(Ghost ghost) {
        return false;
    }

    @Override
    public double speed() {
        return SPEED;
    }
}
