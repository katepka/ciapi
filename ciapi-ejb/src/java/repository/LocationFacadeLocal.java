package repository;

import entity.Location;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами 
 * типа Location - создание, редактирование, удаление, 
 * поиск по первичному ключу, выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface LocationFacadeLocal {

    void create(Location location);

    void edit(Location location);

    Location remove(Object id);

    Location find(Object id);
    
    List<Location> findByName(String name);

    List<Location> findAll();

    List<Location> findRange(int[] range);

    long count();
    
}
