package repository;

import entity.Comment;
import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CommentFacade extends AbstractFacade<Comment> implements CommentFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentFacade() {
        super(Comment.class);
    }

    @Override
    public List<Comment> findByAuthor(long authorId) {
        return em.createNamedQuery("Comment.findByAuthor")
                .setParameter("authorId", authorId)
                .getResultList();
    }
    
}
