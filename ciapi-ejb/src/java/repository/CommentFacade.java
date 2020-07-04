package repository;

import entity.Comment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Сессионный EJB. Определяет CRUD-операции над объектами типа Comment.
 * Расширяет AbstractFacade<T>
 * @author Теплякова Е.А.
 */
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

    @Override
    public List<Comment> findByIdea(long ideaId) {
        return em.createNamedQuery("Comment.findByIdea")
                .setParameter("ideaId", ideaId)
                .getResultList();
    } 
}
