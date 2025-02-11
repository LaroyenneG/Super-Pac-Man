package model.entity.food.ability;

import model.GameAbilityInterface;
import model.entity.individual.pac.person.PacPerson;

public interface Ability {

    void apply(PacPerson owner, GameAbilityInterface grid);


}
