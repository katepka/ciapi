package repository;

import entity.Status;
import java.util.List;
import javax.ejb.Local;

@Local
public interface StatusFacadeLocal {

    void create(Status status);

    void edit(Status status);

    Status remove(Object id);

    Status find(Object id);

    List<Status> findAll();

    List<Status> findRange(int[] range);

    long count();
    
}
