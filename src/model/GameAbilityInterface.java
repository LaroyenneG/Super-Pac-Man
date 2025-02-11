package model;

import model.entity.individual.pac.person.PacPerson;

public interface GameAbilityInterface {

    void evolve(PacPerson pacPerson);

    void scareOffGhosts();

    void miniaturizePacPeople();
}
