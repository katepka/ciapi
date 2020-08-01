package repository;

import entity.Photo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Сессионный EJB. Определяет CRUD-операции над объектами типа Photo.
 * Расширяет AbstractFacade<T>
 * @author Теплякова Е.А.
 */
@Stateless
public class PhotoFacade extends AbstractFacade<Photo> implements PhotoFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhotoFacade() {
        super(Photo.class);
    }
    
}
