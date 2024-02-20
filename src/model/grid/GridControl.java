package model.grid;

import model.entity.individual.ghost.Ghost;
import model.entity.individual.pac.person.PacPerson;

import java.util.Set;

public interface GridControl {

    public void evolve(PacPerson pacPerson);

    public Set<Ghost> getGhosts();
}
