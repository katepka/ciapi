package repository;

import entity.Comment;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CommentFacadeLocal {

    void create(Comment comment);

    void edit(Comment comment);

    Comment remove(Object id);

    Comment find(Object id);

    List<Comment> findAll();

    List<Comment> findRange(int[] range);
    
    List<Comment> findByAuthor(long authorId);

    long count();
    
}
