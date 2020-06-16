package repository;

import entity.ImplementationInfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ImplementationInfoFacade extends AbstractFacade<ImplementationInfo> implements ImplementationInfoFacadeLocal {

    @PersistenceContext(unitName = "ciapi-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImplementationInfoFacade() {
        super(ImplementationInfo.class);
    }
    
}
