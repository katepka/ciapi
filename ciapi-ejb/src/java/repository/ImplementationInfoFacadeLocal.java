package repository;

import entity.ImplementationInfo;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами 
 * типа ImplementationInfo - создание, редактирование, 
 * удаление, поиск по первичному ключу, выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface ImplementationInfoFacadeLocal {

    void create(ImplementationInfo implementationInfo);

    void edit(ImplementationInfo implementationInfo);

    ImplementationInfo remove(Object id);

    ImplementationInfo find(Object id);

    List<ImplementationInfo> findAll();

    List<ImplementationInfo> findRange(int[] range);

    long count();
    
}
