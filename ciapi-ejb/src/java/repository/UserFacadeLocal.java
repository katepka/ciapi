package repository;

import entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами 
 * типа User - создание, редактирование, удаление, 
 * поиск по первичному ключу, выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    User remove(Object id);

    User find(Object id);
    
    User findUser(String email, String password);
    
    List<User> findByRole(long roleId);

    List<User> findAll();

    List<User> findRange(int[] range);

    long count();
    
}
