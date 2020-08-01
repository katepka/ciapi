package repository;

import entity.Role;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами 
 * типа Role - создание, редактирование, удаление, 
 * поиск по первичному ключу, выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface RoleFacadeLocal {

    void create(Role role);

    void edit(Role role);

    Role remove(Object id);

    Role find(Object id);

    List<Role> findAll();

    List<Role> findRange(int[] range);

    long count();
    
}
