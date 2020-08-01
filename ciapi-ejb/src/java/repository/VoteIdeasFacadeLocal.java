package repository;

import entity.VoteIdeas;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами 
 * типа VoteIdeas - создание, редактирование, удаление, 
 * поиск по первичному ключу, выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface VoteIdeasFacadeLocal {

    void create(VoteIdeas votesIdeas);

    void edit(VoteIdeas votesIdeas);

    VoteIdeas remove(Object id);

    VoteIdeas find(Object id);

    List<VoteIdeas> findAll();

    List<VoteIdeas> findRange(int[] range);

    long count();
    
    List<VoteIdeas> findVote(Long userId, Long ideaId);
    
}
