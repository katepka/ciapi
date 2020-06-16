package repository;

import entity.Location;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LocationFacadeLocal {

    void create(Location location);

    void edit(Location location);

    Location remove(Object id);

    Location find(Object id);
    
    List<Location> findByName(String name);

    List<Location> findAll();

    List<Location> findRange(int[] range);

    int count();
    
}
