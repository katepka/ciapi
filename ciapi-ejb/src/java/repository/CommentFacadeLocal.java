package repository;

import entity.Comment;
import java.util.List;
import javax.ejb.Local;

/**
 * Локальный интерфейс, задающий операции над объектами типа Comment 
 * - создание, редактирование, удаление, поиск по первичному ключу, 
 * выборка всех сущностей и другие.
 * @author Теплякова Е.А.
 */
@Local
public interface CommentFacadeLocal {

    void create(Comment comment);

    void edit(Comment comment);

    Comment remove(Object id);

    Comment find(Object id);

    List<Comment> findAll();

    List<Comment> findRange(int[] range);
    
    List<Comment> findByAuthor(long authorId);
    
    List<Comment> findByIdea(long ideaId);

    long count();
    
}
