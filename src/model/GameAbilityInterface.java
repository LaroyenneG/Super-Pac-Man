package model;

import model.entity.individual.pac.person.PacPerson;

public interface GameAbilityInterface {

    public void evolve(PacPerson pacPerson);

    public void scareOffGhosts();

    public void miniaturizePacPeople() ;
}
