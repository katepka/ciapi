package repository;

import entity.User;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    User remove(Object id);

    User find(Object id);
    
    List<User> findByRole(long roleId);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    
}
