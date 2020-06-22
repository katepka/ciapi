package repository;

import entity.VoteIdeas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VotesIdeasFacade extends AbstractFacade<VoteIdeas> implements VotesIdeasFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotesIdeasFacade() {
        super(VoteIdeas.class);
    }
    
    public List<VoteIdeas> findVote(Long userId, Long ideaId) {
        return em.createNamedQuery("VotesIdeas.findByUserIdAndRatedIdeaId")
                .setParameter("userId", userId)
                .setParameter("ratedIdeaId", ideaId)
                .getResultList();
    }

}
