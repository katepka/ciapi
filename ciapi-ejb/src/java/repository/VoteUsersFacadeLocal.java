package repository;

import entity.VotesUsers;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами 
 * типа VotesUsers - создание, редактирование, удаление, 
 * поиск по первичному ключу, выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface VoteUsersFacadeLocal {

    void create(VotesUsers votesUsers);

    void edit(VotesUsers votesUsers);

    VotesUsers remove(Object id);

    VotesUsers find(Object id);

    List<VotesUsers> findAll();

    List<VotesUsers> findRange(int[] range);

    long count();
    
}
