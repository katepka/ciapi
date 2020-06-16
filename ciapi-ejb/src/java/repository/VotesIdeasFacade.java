package repository;

import entity.VotesIdeas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VotesIdeasFacade extends AbstractFacade<VotesIdeas> implements VotesIdeasFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotesIdeasFacade() {
        super(VotesIdeas.class);
    }
    
}
