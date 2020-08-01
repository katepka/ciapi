package repository;

import entity.Photo;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами 
 * типа Photo - создание, редактирование, удаление, 
 * поиск по первичному ключу, выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface PhotoFacadeLocal {

    void create(Photo photo);

    void edit(Photo photo);

    Photo remove(Object id);

    Photo find(Object id);

    List<Photo> findAll();

    List<Photo> findRange(int[] range);

    long count();
    
}
