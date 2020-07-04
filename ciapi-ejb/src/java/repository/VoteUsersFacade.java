package repository;

import entity.VotesUsers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VoteUsersFacade extends AbstractFacade<VotesUsers> implements VoteUsersFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VoteUsersFacade() {
        super(VotesUsers.class);
    }
    
}
