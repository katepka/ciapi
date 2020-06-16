package repository;

import entity.VotesUsers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VotesUsersFacade extends AbstractFacade<VotesUsers> implements VotesUsersFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotesUsersFacade() {
        super(VotesUsers.class);
    }
    
}
