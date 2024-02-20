package model.grid;

import model.entity.individual.pac.person.PacPerson;

public interface GridAbilityInterface {

    public void evolve(PacPerson pacPerson);

    public void scareOffGhosts();

    public void miniaturizePacPeople() ;
}
