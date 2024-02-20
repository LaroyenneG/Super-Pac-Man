package model.entity.food.ability;

import model.entity.individual.pac.person.PacPerson;
import model.GameAbilityInterface;

public interface Ability {

    void apply(PacPerson owner, GameAbilityInterface grid);

    
}
