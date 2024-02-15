package model.game.entity.individual.ghost;

import model.game.entity.individual.Individual;
import model.game.entity.individual.pac.person.PacPerson;
import model.game.entity.food.Food;
import stdlib.StdRandom;

import java.awt.*;

public abstract class Ghost extends Individual {

    private final Color color;
    private boolean scared = StdRandom.bernoulli();


    public Ghost(Color color) {
        this.color = color;
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

    public Color getColor() {
        return this.color;
    }
}
