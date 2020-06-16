package repository;

import entity.ImplementationInfo;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ImplementationInfoFacadeLocal {

    void create(ImplementationInfo implementationInfo);

    void edit(ImplementationInfo implementationInfo);

    ImplementationInfo remove(Object id);

    ImplementationInfo find(Object id);

    List<ImplementationInfo> findAll();

    List<ImplementationInfo> findRange(int[] range);

    int count();
    
}
