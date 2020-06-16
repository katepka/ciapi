package repository;

import entity.Photo;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PhotoFacadeLocal {

    void create(Photo photo);

    void edit(Photo photo);

    Photo remove(Object id);

    Photo find(Object id);

    List<Photo> findAll();

    List<Photo> findRange(int[] range);

    int count();
    
}
