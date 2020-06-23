package repository;

import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public List<User> findByRole(long roleId) {
        return em.createNamedQuery("User.findByRole")
                .setParameter("roleId", roleId)
                .getResultList();
    }

    @Override
    public User findUser(String email, String password) {
        return (User) em.createNamedQuery("User.findUser")
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
    
}
