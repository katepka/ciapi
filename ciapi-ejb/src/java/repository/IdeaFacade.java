package repository;

import entity.Idea;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class IdeaFacade extends AbstractFacade<Idea> implements IdeaFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IdeaFacade() {
        super(Idea.class);
    }

    @Override
    public List<Idea> findByCategory(long categoryId) {
        return em.createNamedQuery("Idea.findByCategory")
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<Idea> findByStatus(long statusId) {
        return em.createNamedQuery("Idea.findByStatus")
                .setParameter("statusId", statusId)
                .getResultList();
    }

    @Override
    public List<Idea> findByAuthor(long authorId) {
        return em.createNamedQuery("Idea.findByAuthor")
                .setParameter("authorId", authorId)
                .getResultList();
    }

    @Override
    public List<Idea> findByCoordinator(long coordinatorId) {
        return em.createNamedQuery("Idea.findByCoordinator")
                .setParameter("coordinatorId", coordinatorId)
                .getResultList();
    }

    @Override
    public List<Idea> findByLocation(long locationId) {
        return em.createNamedQuery("Idea.findByLocation")
                .setParameter("locationId", locationId)
                .getResultList();
    }

    @Override
    public List<Idea> findByTitle(String title) {
        return em.createNamedQuery("Idea.findByTitle")
                .setParameter("title", title)
                .getResultList();
    }

}
